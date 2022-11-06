package com.jacksafblaze.storeowner.domain.usecase

import com.jacksafblaze.storeowner.domain.model.Product

interface AddProductToCategoryUseCase {
    suspend fun execute(product: Product, categoryId: String): Boolean
}