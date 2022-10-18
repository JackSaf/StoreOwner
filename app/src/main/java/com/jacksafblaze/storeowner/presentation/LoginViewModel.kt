package com.jacksafblaze.storeowner.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacksafblaze.storeowner.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val sendVerificationEmailUseCase: SendVerificationEmailUseCase,
    private val checkIfUserVerifiedUseCase: CheckIfUserVerifiedUseCase,
    private val checkIfUserLoggedInUseCase: CheckIfUserLoggedInUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    init {
        isUserLoggedIn()
        isUserVerified()
    }
    private fun isUserLoggedIn() = viewModelScope.launch {
        checkIfUserLoggedInUseCase.execute().collect{ loggedIn ->
            _uiState.update {
                it.copy(isLoggedIn = loggedIn)
            }
        }
    }
    private fun isUserVerified() = viewModelScope.launch {
        checkIfUserVerifiedUseCase.execute().collect{ verified ->
            _uiState.update {
                it.copy(isVerified = verified)
            }
        }
    }
    fun userMessageShown() {
        _uiState.update {
            it.copy(message = null)
        }
    }

    fun login() = viewModelScope.launch {
        _uiState.update {
            it.copy(isLoading = true)
        }
        val email = _uiState.value.email
        val password = _uiState.value.password
        try {
            loginUseCase.execute(email!!, password!!)
        } catch (e: Exception) {
            _uiState.update {
                it.copy(message = "${e.message}")
            }
        }
        _uiState.update {
            it.copy(isLoading = false)
        }
    }

    fun register() = viewModelScope.launch {
        _uiState.update {
            it.copy(isLoading = true)
        }
        val email = _uiState.value.email
        val password = _uiState.value.password
        try {
            registerUseCase.execute(email!!, password!!)
        } catch (e: Exception) {
            _uiState.update {
                it.copy(message = "${e.message}")
            }
        }
        _uiState.update {
            it.copy(isLoading = false)
        }
    }

    fun validateEmail(email: String?) {
        val regex = "^([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5})$".toRegex()
        if (email.isNullOrBlank()) {
            _uiState.update {
                it.copy(emailAlert = "Please enter email address", isEmailOk = false)
            }
        } else if (!regex.matches(email)) {
            _uiState.update {
                it.copy(emailAlert = "Incorrect email address")
            }
        } else {
            _uiState.update {
                it.copy(emailAlert = null, isEmailOk = true, email = email)
            }
        }
    }

    fun validatePassword(password: String?) {
        if (password.isNullOrBlank()) {
            _uiState.update {
                it.copy(passwordAlert = "Please enter password", isPasswordOk = false)
            }
        }
        else if (password.length < 8) {
            _uiState.update {
                it.copy(passwordAlert = "Password must be 8 or more characters long", isPasswordOk = false)
            }
        }
        else {
            _uiState.update {
                it.copy(passwordAlert = null, isPasswordOk = true, password = password)
            }
        }
    }

    fun sendVerificationEmail() = viewModelScope.launch{
        if(sendVerificationEmailUseCase.execute()) {
            _uiState.update {
                it.copy(message = "Verification email was sent to ${_uiState.value.email}")
            }
        }
    }

    fun logout(){
        logoutUseCase.execute()
    }
}