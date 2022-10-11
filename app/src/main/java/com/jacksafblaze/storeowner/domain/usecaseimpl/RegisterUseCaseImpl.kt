package com.jacksafblaze.storeowner.domain.usecaseimpl

import com.jacksafblaze.storeowner.domain.repository.LoginRepository
import com.jacksafblaze.storeowner.domain.usecase.RegisterUseCase

class RegisterUseCaseImpl(private val repository: LoginRepository) : RegisterUseCase {
    override suspend fun execute(email: String, password: String): Boolean {
        return repository.register(email, password)
    }
}