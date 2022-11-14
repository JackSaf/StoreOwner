package com.jacksafblaze.storeowner.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacksafblaze.storeowner.domain.usecase.login.*
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
            if(checkIfUserLoggedInUseCase.execute()){
                _uiState.update {
                    it.copy(isLoggedIn = true)
                }
                if(checkIfUserVerifiedUseCase.execute()){
                    _uiState.update {
                        it.copy(isVerified = true)
                    }
                }
                else{
                    sendVerificationEmail()
                    logout()
                }
            }
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
            if(checkIfUserLoggedInUseCase.execute()){
                _uiState.update {
                    it.copy(isLoggedIn = true)
                }
                sendVerificationEmail()
                logout()
            }
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
                it.copy(emailAlert = "Please enter email address")
            }
        } else if (!regex.matches(email)) {
            _uiState.update {
                it.copy(emailAlert = "Incorrect email address")
            }
        } else {
            _uiState.update {
                it.copy(emailAlert = null, email = email)
            }
        }
    }

    fun validatePassword(password: String?) {
        if (password.isNullOrBlank()) {
            _uiState.update {
                it.copy(passwordAlert = "Please enter password")
            }
        }
        else if (password.length < 8) {
            _uiState.update {
                it.copy(passwordAlert = "Password must be 8 or more characters long")
            }
        }
        else {
            _uiState.update {
                it.copy(passwordAlert = null, password = password)
            }
        }
    }

    private suspend fun sendVerificationEmail(){
        if(sendVerificationEmailUseCase.execute()) {
            _uiState.update {
                it.copy(message = "Verification email was sent to ${_uiState.value.email}")
            }
        }
    }

    private fun logout(){
        logoutUseCase.execute()
        _uiState.update {
            it.copy(isLoggedIn = false)
        }
    }
}