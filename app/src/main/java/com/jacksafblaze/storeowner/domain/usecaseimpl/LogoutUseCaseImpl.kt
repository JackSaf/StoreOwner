package com.jacksafblaze.storeowner.domain.usecaseimpl

import com.jacksafblaze.storeowner.domain.repository.LoginRepository
import com.jacksafblaze.storeowner.domain.usecase.LogoutUseCase

class LogoutUseCaseImpl(private val repository: LoginRepository) : LogoutUseCase {
    override fun execute() {
        repository.logout()
    }
}