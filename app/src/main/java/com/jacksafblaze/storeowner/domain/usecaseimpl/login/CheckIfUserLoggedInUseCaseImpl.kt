package com.jacksafblaze.storeowner.domain.usecaseimpl.login

import com.jacksafblaze.storeowner.domain.repository.LoginRepository
import com.jacksafblaze.storeowner.domain.usecase.login.CheckIfUserLoggedInUseCase

class CheckIfUserLoggedInUseCaseImpl(private val repository: LoginRepository) :
    CheckIfUserLoggedInUseCase {
    override fun execute(): Boolean {
        return repository.isUserLoggedIn()
    }
}