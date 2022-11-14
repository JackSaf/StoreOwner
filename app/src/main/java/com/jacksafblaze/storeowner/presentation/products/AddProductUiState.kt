package com.jacksafblaze.storeowner.presentation.products

import android.net.Uri

data class AddProductUiState(
    val categoryId: String? = null,
    val imageUri: Uri? = null,
    val name: String? = null,
    val cost: String? = null,
    val price: String? = null,
    val unit: String? = null,
    val message: String? = null)