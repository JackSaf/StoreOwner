package com.jacksafblaze.storeowner.domain.usecase

import com.jacksafblaze.storeowner.domain.model.Product

interface DeleteProductFromCategoryUseCase {
    suspend fun execute(product: Product, categoryId: String): Boolean
}