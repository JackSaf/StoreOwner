package com.jacksafblaze.storeowner.domain.usecaseimpl

import com.jacksafblaze.storeowner.domain.repository.LoginRepository
import com.jacksafblaze.storeowner.domain.usecase.LoginUseCase

class LoginUseCaseImpl(private val repository: LoginRepository) : LoginUseCase {
    override suspend fun execute(email: String, password: String): Boolean {
        return repository.login(email, password)
    }
}