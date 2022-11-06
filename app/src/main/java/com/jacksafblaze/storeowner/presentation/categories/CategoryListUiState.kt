package com.jacksafblaze.storeowner.presentation.categories

import com.jacksafblaze.storeowner.domain.model.Category


data class CategoryListUiState(
    val categories: List<Category> = listOf(),
    val isLoading: Boolean = false,
    val message: String? = null
)