package com.jacksafblaze.storeowner.domain.usecaseimpl.products

import com.jacksafblaze.storeowner.domain.model.Product
import com.jacksafblaze.storeowner.domain.repository.ProductRepository
import com.jacksafblaze.storeowner.domain.usecase.products.UpdateProductInCategoryUseCase

class UpdateProductInCategoryUseCaseImpl(private val repository: ProductRepository) :
    UpdateProductInCategoryUseCase {
    override suspend fun execute(product: Product, categoryId: String): Boolean {
        return repository.updateProduct(product, categoryId)
    }
}