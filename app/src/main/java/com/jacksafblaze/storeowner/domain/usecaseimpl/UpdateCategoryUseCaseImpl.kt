package com.jacksafblaze.storeowner.domain.usecaseimpl

import com.jacksafblaze.storeowner.domain.model.Category
import com.jacksafblaze.storeowner.domain.repository.CategoryRepository
import com.jacksafblaze.storeowner.domain.usecase.UpdateCategoryUseCase

class UpdateCategoryUseCaseImpl(private val repository: CategoryRepository) : UpdateCategoryUseCase {
    override suspend fun execute(category: Category): Boolean {
        return repository.updateCategory(category)
    }
}