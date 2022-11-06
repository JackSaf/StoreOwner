package com.jacksafblaze.storeowner.presentation.categories

import android.net.Uri

data class AddCategoryUiState(
    val imageUri: Uri? = null,
    val name: String? = null,
    val isNameOk: Boolean = false,
    val nameAlert: String? = null,
    val isLoading: Boolean = false,
    val isCategoryAdded: Boolean = false,
    val message: String? = null){
    val isAddButtonEnabled get() = isNameOk
}