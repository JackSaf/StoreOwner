package com.jacksafblaze.storeowner.presentation

data class LoginUiState(
    val email: String? = null,
    val password: String? = null,
    val isLoading: Boolean = false,
    val message: String? = null
)