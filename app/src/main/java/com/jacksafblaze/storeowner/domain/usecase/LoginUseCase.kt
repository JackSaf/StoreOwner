package com.jacksafblaze.storeowner.domain.usecase

interface LoginUseCase {
    suspend fun execute(email: String, password: String): Boolean
}