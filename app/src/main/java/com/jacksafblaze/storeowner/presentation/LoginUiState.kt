package com.jacksafblaze.storeowner.presentation

data class LoginUiState(
    val email: String? = null,
    val password: String? = null,
    val isLoading: Boolean = false,
    val emailAlert: String? = null,
    val passwordAlert: String? = null,
    val message: String? = null)