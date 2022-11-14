package com.jacksafblaze.storeowner.data.repository

import android.net.Uri
import android.util.Patterns
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.jacksafblaze.storeowner.domain.model.Category
import com.jacksafblaze.storeowner.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.coroutines.tasks.await

class CategoryRepositoryImpl(firebaseAuth: FirebaseAuth,
                             firebaseDatabase: FirebaseDatabase,
                             cloudStorage: FirebaseStorage
                            ): CategoryRepository {
    private val categoriesStorageRef = cloudStorage.reference.child(firebaseAuth.uid!!).child("categories")
    private val categoriesRef = firebaseDatabase.reference.child(firebaseAuth.uid!!).child("categories")
    private val productsRef = firebaseDatabase.reference.child(firebaseAuth.uid!!).child("products")
    override suspend fun addCategory(category: Category): Boolean {
        return withContext(Dispatchers.IO) {
            val newCategoryRef = categoriesRef.push()
            val newCategoryId = newCategoryRef.key
            val imageUrl = uploadCategoryImage(category.imageUri.toUri(), newCategoryId!!)
            val taskSetInCategories = newCategoryRef.setValue(category.copy(id = newCategoryId, imageUri = imageUrl))
            taskSetInCategories.await()
            val success = taskSetInCategories.isSuccessful
            success
        }
    }

    override fun getCategories(): Flow<List<Category>> = callbackFlow {
        val listener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = arrayListOf<Category>()
                for(productSnapshot in snapshot.children){
                    list.add(productSnapshot.getValue(Category::class.java) ?: Category())
                }
                trySend(list)
            }

            override fun onCancelled(error: DatabaseError) {
                throw(error.toException())
            }
        }
        categoriesRef.addValueEventListener(listener)
        awaitClose{
            categoriesRef.removeEventListener(listener)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteCategory(category: Category): Boolean {
        return withContext(Dispatchers.IO) {
            val taskGet = categoriesRef.child(category.id).get()
            if (!taskGet.await().exists()) {
                throw Exception("Category with id ${category.id} doesn't exist")
            }
            val taskRemove = categoriesRef.child(category.id).removeValue()
            deleteCategoryImage(categoryId = category.id)
            productsRef.child(category.id).removeValue().await()
            taskRemove.await()
            taskRemove.isSuccessful
        }
    }

    override suspend fun updateCategory(category: Category): Boolean {
        return withContext(Dispatchers.IO) {
            val taskGet = categoriesRef.child(category.id).get()
            val categorySnap = taskGet.await()
            if (categorySnap.exists()) {
                throw Exception("Category with id ${category.id} doesn't exist")
            }
            if(!Patterns.WEB_URL.matcher(category.imageUri).matches()){
                uploadCategoryImage(category.imageUri.toUri(), category.id)
            }
            val taskUpdate = categoriesRef.child(category.id).setValue(category)
            taskUpdate.isSuccessful
        }
    }

    private suspend fun uploadCategoryImage(imageUri: Uri, categoryId: String): String {
        return if(imageUri.toString().isNotBlank()) {
            val picture = categoriesStorageRef.child(categoryId).putFile(imageUri).await()
            val pictureUrl = picture.metadata?.reference?.downloadUrl?.await()
            pictureUrl!!.toString()
        } else{
            ""
        }
    }

    private suspend fun deleteCategoryImage(categoryId: String){
        categoriesStorageRef.child(categoryId).delete().await()
    }
}