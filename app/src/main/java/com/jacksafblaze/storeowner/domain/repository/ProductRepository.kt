package com.jacksafblaze.storeowner.domain.repository

import android.net.Uri
import com.jacksafblaze.storeowner.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun addProductToCategory(product: Product, categoryId: String): Boolean
    fun getProducts(categoryId: String): Flow<List<Product>>
    suspend fun deleteProduct(product: Product, categoryId: String): Boolean
    suspend fun updateProduct(product: Product, categoryId: String): Boolean
}