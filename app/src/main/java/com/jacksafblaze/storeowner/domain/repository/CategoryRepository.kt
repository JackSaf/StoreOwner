package com.jacksafblaze.storeowner.domain.repository

import android.net.Uri
import com.jacksafblaze.storeowner.domain.model.Category
import com.jacksafblaze.storeowner.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun addCategory(category: Category): Boolean
    fun getCategories(): Flow<List<Category>>
    suspend fun deleteCategory(category: Category): Boolean
    suspend fun updateCategory(category: Category): Boolean
}