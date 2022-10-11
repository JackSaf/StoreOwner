package com.jacksafblaze.storeowner.domain.usecaseimpl

import com.jacksafblaze.storeowner.domain.repository.LoginRepository
import com.jacksafblaze.storeowner.domain.usecase.CheckIfLoggedInUseCase

class CheckIfLoggedInUseCaseImpl(private val repository: LoginRepository) : CheckIfLoggedInUseCase {
    override fun checkIfLoggedIn(): Boolean {
        return repository.isUserLoggedIn()
    }
}