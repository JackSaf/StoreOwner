package com.jacksafblaze.storeowner.domain.usecaseimpl.login

import com.jacksafblaze.storeowner.domain.repository.LoginRepository
import com.jacksafblaze.storeowner.domain.usecase.login.RegisterUseCase

class RegisterUseCaseImpl(private val repository: LoginRepository) : RegisterUseCase {
    override suspend fun execute(email: String, password: String): Boolean {
        return repository.register(email, password)
    }
}