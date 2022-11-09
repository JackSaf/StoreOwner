package com.jacksafblaze.storeowner.domain.usecase.login

interface LoginUseCase {
    suspend fun execute(email: String, password: String): Boolean
}