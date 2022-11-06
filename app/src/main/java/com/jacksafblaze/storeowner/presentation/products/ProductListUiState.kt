package com.jacksafblaze.storeowner.presentation.products

import com.jacksafblaze.storeowner.domain.model.Product

data class ProductListUiState(val isLoading: Boolean = false,
                              val message: String? = null,
                              val categoryId: String? = null,
                              val products: List<Product> = listOf())