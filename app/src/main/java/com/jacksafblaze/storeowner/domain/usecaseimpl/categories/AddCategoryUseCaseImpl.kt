package com.jacksafblaze.storeowner.domain.usecaseimpl.categories

import com.jacksafblaze.storeowner.domain.model.Category
import com.jacksafblaze.storeowner.domain.repository.CategoryRepository
import com.jacksafblaze.storeowner.domain.usecase.categories.AddCategoryUseCase

class AddCategoryUseCaseImpl(private val repository: CategoryRepository) : AddCategoryUseCase {
    override suspend fun execute(category: Category): Boolean {
        return repository.addCategory(category)
    }
}