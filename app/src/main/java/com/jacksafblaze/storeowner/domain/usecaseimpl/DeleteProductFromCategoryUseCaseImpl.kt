package com.jacksafblaze.storeowner.domain.usecaseimpl

import com.jacksafblaze.storeowner.domain.model.Product
import com.jacksafblaze.storeowner.domain.repository.ProductRepository
import com.jacksafblaze.storeowner.domain.usecase.DeleteProductFromCategoryUseCase

class DeleteProductFromCategoryUseCaseImpl(private val repository: ProductRepository) : DeleteProductFromCategoryUseCase {
    override suspend fun execute(product: Product, categoryId: String): Boolean {
        return repository.deleteProduct(product, categoryId)
    }
}