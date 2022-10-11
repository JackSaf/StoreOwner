package com.jacksafblaze.storeowner.domain.usecase

interface RegisterUseCase {
    suspend fun execute(email: String, password: String): Boolean
}