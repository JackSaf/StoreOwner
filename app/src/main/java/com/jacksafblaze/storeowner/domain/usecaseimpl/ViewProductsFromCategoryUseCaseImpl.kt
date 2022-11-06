package com.jacksafblaze.storeowner.domain.usecaseimpl

import com.jacksafblaze.storeowner.domain.model.Product
import com.jacksafblaze.storeowner.domain.repository.ProductRepository
import com.jacksafblaze.storeowner.domain.usecase.ViewProductsFromCategoryUseCase
import kotlinx.coroutines.flow.Flow

class ViewProductsFromCategoryUseCaseImpl(private val repository: ProductRepository) : ViewProductsFromCategoryUseCase {
    override fun execute(categoryId: String): Flow<List<Product>> {
        return repository.getProducts(categoryId)
    }
}