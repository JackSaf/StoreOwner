package com.jacksafblaze.storeowner.presentation.login

data class LoginUiState(
    val email: String? = null,
    val password: String? = null,
    val isLoading: Boolean = false,
    val isEmailOk: Boolean = false,
    val isPasswordOk: Boolean = false,
    val emailAlert: String? = null,
    val passwordAlert: String? = null,
    val isLoggedIn: Boolean = false,
    val isVerified: Boolean = false,
    val message: String? = null){
    val buttonsEnabled get() = isEmailOk && isPasswordOk
}