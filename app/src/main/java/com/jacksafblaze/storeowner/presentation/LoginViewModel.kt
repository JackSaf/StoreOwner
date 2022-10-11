package com.jacksafblaze.storeowner.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacksafblaze.storeowner.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: LoginUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun userMessageShown() {
        _uiState.update {
            it.copy(message = null)
        }
    }

    fun login() = viewModelScope.launch {
        val email = _uiState.value.email
        val password = _uiState.value.password
        if (validateEmail(email) && validatePassword(password)) {
            try {
                loginUseCase.execute(email!!, password!!)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(message = "${e.message}")
                }
            }
        }
    }

    fun register() = viewModelScope.launch {
        val email = _uiState.value.email
        val password = _uiState.value.password
        if (validateEmail(email) && validatePassword(password)) {
            try {
                registerUseCase.execute(email!!, password!!)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(message = "${e.message}")
                }
            }
        }
    }

    private fun validateEmail(email: String?): Boolean {
        if (email.isNullOrBlank()) {
            _uiState.update {
                it.copy(message = "Please enter email")
            }
            return false
        }
        val regex = "^([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5})$".toRegex()
        if (!regex.matches(email)) {
            _uiState.update {
                it.copy(message = "Wrong email")
            }
            return false
        }
        return true
    }

    private fun validatePassword(password: String?): Boolean {
        if (password.isNullOrBlank()) {
            _uiState.update {
                it.copy(message = "Please enter password")
            }
            return false
        }
        if (password.length < 8) {
            _uiState.update {
                it.copy(message = "Password must be 8 or more characters long")
            }
            return false
        }
        return true
    }
}