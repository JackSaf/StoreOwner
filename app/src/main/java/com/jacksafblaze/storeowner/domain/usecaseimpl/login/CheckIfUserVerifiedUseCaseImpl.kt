package com.jacksafblaze.storeowner.domain.usecaseimpl.login

import com.jacksafblaze.storeowner.domain.repository.LoginRepository
import com.jacksafblaze.storeowner.domain.usecase.login.CheckIfUserVerifiedUseCase

class CheckIfUserVerifiedUseCaseImpl(private val repository: LoginRepository):
    CheckIfUserVerifiedUseCase {
    override fun execute(): Boolean {
        return repository.isUserVerified()
    }
}