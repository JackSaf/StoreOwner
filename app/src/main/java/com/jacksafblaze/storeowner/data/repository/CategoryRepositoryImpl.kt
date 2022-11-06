package com.jacksafblaze.storeowner.data.repository

import android.net.Uri
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.coroutines.tasks.await

class CategoryRepositoryImpl(firebaseAuth: FirebaseAuth,
                             firebaseDatabase: FirebaseDatabase,
                             cloudStorage: FirebaseStorage
                            ): CategoryRepository {
    private val storageCategoriesRef = cloudStorage.reference.child(firebaseAuth.uid!!).child("categories")
    private val categoriesRef = firebaseDatabase.reference.child(firebaseAuth.uid!!).child("categories")
    override suspend fun addCategory(category: Category): Boolean {
        return withContext(Dispatchers.IO) {
            val newCategoryRef = categoriesRef.push()
            val newCategoryId = newCategoryRef.key
            val imageUrl = uploadCategoryImage(category.imageUri.toUri())
            val taskSet = newCategoryRef.setValue(category.copy(id = newCategoryId!!, imageUri = imageUrl))
            taskSet.await()
            taskSet.isSuccessful
        }
    }

    override fun getCategories(): Flow<List<Category>> = callbackFlow {
        categoriesRef.addValueEventListener(object: ValueEventListener {
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
        })
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteCategory(category: Category): Boolean {
        return withContext(Dispatchers.IO) {
            val taskGet = categoriesRef.child(category.id).get()
            if (!taskGet.await().exists()) {
                throw Exception("Category with id ${category.id} doesn't exist")
            }
            val taskRemove = categoriesRef.child(category.id).removeValue()
            //TODO(Delete image)
            //Todo(Add automatic deleting of category in products node)
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
            //TODO(Delete previous image, add a new one)
            val taskUpdate = categoriesRef.child(category.id).setValue(category)
            taskUpdate.isSuccessful
        }
    }

    override suspend fun uploadCategoryImage(imageUri: Uri): String {
        return withContext(Dispatchers.IO){
            val picture = storageCategoriesRef.putFile(imageUri).await()
            val pictureUrl = picture.metadata?.reference?.downloadUrl?.await()
            pictureUrl!!.toString()
        }
    }
}