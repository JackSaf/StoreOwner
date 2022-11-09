package com.jacksafblaze.storeowner.domain.usecaseimpl.products

import com.jacksafblaze.storeowner.domain.model.Product
import com.jacksafblaze.storeowner.domain.repository.ProductRepository
import com.jacksafblaze.storeowner.domain.usecase.products.AddProductToCategoryUseCase

class AddProductToCategoryUseCaseImpl(private val repository: ProductRepository) :
    AddProductToCategoryUseCase {
    override suspend fun execute(product: Product, categoryId: String): Boolean {
        return repository.addProductToCategory(product, categoryId)
    }
}