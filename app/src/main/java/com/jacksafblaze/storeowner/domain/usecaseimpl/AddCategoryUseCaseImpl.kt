package com.jacksafblaze.storeowner.domain.usecaseimpl

import com.jacksafblaze.storeowner.domain.model.Category
import com.jacksafblaze.storeowner.domain.repository.CategoryRepository
import com.jacksafblaze.storeowner.domain.usecase.AddCategoryUseCase

class AddCategoryUseCaseImpl(private val repository: CategoryRepository) : AddCategoryUseCase {
    override suspend fun execute(category: Category): Boolean {
        return repository.addCategory(category)
    }
}