package com.jacksafblaze.storeowner.domain.usecase.products

import com.jacksafblaze.storeowner.domain.model.Product

interface UpdateProductInCategoryUseCase {
    suspend fun execute(product: Product, categoryId: String): Boolean
}