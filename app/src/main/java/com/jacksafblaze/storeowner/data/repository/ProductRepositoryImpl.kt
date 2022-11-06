package com.jacksafblaze.storeowner.data.repository

import android.net.Uri
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.jacksafblaze.storeowner.domain.model.Product
import com.jacksafblaze.storeowner.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ProductRepositoryImpl(
    firebaseAuth: FirebaseAuth,
    firebaseDatabase: FirebaseDatabase,
    cloudStorage: FirebaseStorage
) : ProductRepository {
    private val productsRef = firebaseDatabase.reference.child(firebaseAuth.uid!!).child("products")
    private val storageProductsRef = cloudStorage.reference.child(firebaseAuth.uid!!).child("products")

    override suspend fun addProductToCategory(product: Product, categoryId: String): Boolean {
        return withContext(Dispatchers.IO) {

            val newProductRef = productsRef.child(categoryId).push()
            val newProductKey = newProductRef.key
            val imageUrl = uploadProductImage(product.imageUri.toUri())
            val taskSet = newProductRef.setValue(product.copy(id = newProductKey!!, imageUri = imageUrl))
            taskSet.await()
            taskSet.isSuccessful
        }
    }

    override suspend fun updateProduct(product: Product, categoryId: String): Boolean {
        return withContext(Dispatchers.IO) {
            val taskGet = productsRef.child(categoryId).child(product.id).get()
            if (!taskGet.await().exists()) {
                throw Exception("Product with id ${product.id} doesn't exist")
            }
            //TODO(add new image, delete the old one)
            val taskSet = productsRef.child(categoryId).child(product.id).setValue(product)
            taskSet.await()
            taskSet.isSuccessful
        }
    }

    override suspend fun deleteProduct(product: Product, categoryId: String): Boolean {
        return withContext(Dispatchers.IO) {
            val taskGet = productsRef.child(categoryId).child(product.id).get()
            if (!taskGet.await().exists()) {
                throw Exception("Product with id ${product.id} doesn't exist")
            }
            //TODO(delete image)
            val taskRemove = productsRef.child(categoryId).child(product.id).removeValue()
            taskRemove.await()
            taskRemove.isSuccessful
        }
    }

    override fun getProducts(categoryId: String): Flow<List<Product>> = callbackFlow {
        productsRef.child(categoryId).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = arrayListOf<Product>()
                for(productSnapshot in snapshot.children){
                    list.add(productSnapshot.getValue(Product::class.java) ?: Product())
                }
                trySend(list)
            }

            override fun onCancelled(error: DatabaseError) {
                throw(error.toException())
            }
        })
    }.flowOn(Dispatchers.IO)

    override suspend fun uploadProductImage(imageUri: Uri): String {
        return withContext(Dispatchers.IO){
            val picture = storageProductsRef.putFile(imageUri).await()
            val pictureUrl = picture.metadata?.reference?.downloadUrl?.await()
            pictureUrl!!.toString()
        }
    }

}