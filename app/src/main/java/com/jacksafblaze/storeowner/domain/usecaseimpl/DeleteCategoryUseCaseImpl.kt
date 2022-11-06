package com.jacksafblaze.storeowner.domain.usecaseimpl

import com.jacksafblaze.storeowner.domain.model.Category
import com.jacksafblaze.storeowner.domain.repository.CategoryRepository
import com.jacksafblaze.storeowner.domain.usecase.DeleteCategoryUseCase

class DeleteCategoryUseCaseImpl(private val repository: CategoryRepository) : DeleteCategoryUseCase {
    override suspend fun execute(category: Category): Boolean {
        return repository.deleteCategory(category)
    }
}