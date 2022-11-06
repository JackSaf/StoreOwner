package com.jacksafblaze.storeowner.domain.usecase

import com.jacksafblaze.storeowner.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface ViewCategoriesUseCase {
    fun execute(): Flow<List<Category>>
}