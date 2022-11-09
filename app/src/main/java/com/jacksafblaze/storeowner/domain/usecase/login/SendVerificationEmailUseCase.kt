package com.jacksafblaze.storeowner.domain.usecase.login

interface SendVerificationEmailUseCase {
    suspend fun execute(): Boolean
}