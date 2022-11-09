package com.jacksafblaze.storeowner.domain.usecaseimpl.login

import com.jacksafblaze.storeowner.domain.repository.LoginRepository
import com.jacksafblaze.storeowner.domain.usecase.login.SendVerificationEmailUseCase

class SendVerificationEmailUseCaseImpl(private val repositoryImpl: LoginRepository) :
    SendVerificationEmailUseCase {
    override suspend fun execute(): Boolean {
        return repositoryImpl.sendVerificationEmail()
    }
}