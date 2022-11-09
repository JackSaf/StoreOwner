package com.jacksafblaze.storeowner.domain.usecaseimpl.login

import com.jacksafblaze.storeowner.domain.repository.LoginRepository
import com.jacksafblaze.storeowner.domain.usecase.login.LogoutUseCase

class LogoutUseCaseImpl(private val repository: LoginRepository) : LogoutUseCase {
    override fun execute() {
        repository.logout()
    }
}