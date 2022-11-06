package com.jacksafblaze.storeowner.domain.usecase

import com.jacksafblaze.storeowner.domain.model.Category

interface DeleteCategoryUseCase {
    suspend fun execute(category: Category): Boolean
}