package com.jacksafblaze.storeowner.domain.usecaseimpl

import com.jacksafblaze.storeowner.data.repository.LoginRepositoryImpl
import com.jacksafblaze.storeowner.domain.usecase.SendVerificationEmailUseCase

class SendVerificationEmailUseCaseImpl(private val repositoryImpl: LoginRepositoryImpl) : SendVerificationEmailUseCase {
    override suspend fun execute(): Boolean {
        return repositoryImpl.sendVerificationEmail()
    }
}