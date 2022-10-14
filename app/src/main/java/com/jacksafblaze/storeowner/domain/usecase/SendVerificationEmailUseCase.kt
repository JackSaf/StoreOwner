package com.jacksafblaze.storeowner.domain.usecase

interface SendVerificationEmailUseCase {
    suspend fun execute(): Boolean
}