package com.jacksafblaze.storeowner.domain.usecase

import com.jacksafblaze.storeowner.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ViewProductsFromCategoryUseCase {
    fun execute(categoryId: String): Flow<List<Product>>
}