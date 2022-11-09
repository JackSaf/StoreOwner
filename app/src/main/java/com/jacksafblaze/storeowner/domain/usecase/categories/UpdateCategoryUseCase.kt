package com.jacksafblaze.storeowner.domain.usecase.categories

import com.jacksafblaze.storeowner.domain.model.Category

interface UpdateCategoryUseCase {
    suspend fun execute(category: Category): Boolean
}