package com.jacksafblaze.storeowner.domain.usecaseimpl.categories

import com.jacksafblaze.storeowner.domain.model.Category
import com.jacksafblaze.storeowner.domain.repository.CategoryRepository
import com.jacksafblaze.storeowner.domain.usecase.categories.ViewCategoriesUseCase
import kotlinx.coroutines.flow.Flow

class ViewCategoriesUseCaseImpl(private val repository: CategoryRepository) :
    ViewCategoriesUseCase {
    override fun execute(): Flow<List<Category>> {
        return repository.getCategories()
    }
}