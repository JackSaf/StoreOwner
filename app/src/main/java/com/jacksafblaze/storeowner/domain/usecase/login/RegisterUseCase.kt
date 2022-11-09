package com.jacksafblaze.storeowner.domain.usecase.login

interface RegisterUseCase {
    suspend fun execute(email: String, password: String): Boolean
}