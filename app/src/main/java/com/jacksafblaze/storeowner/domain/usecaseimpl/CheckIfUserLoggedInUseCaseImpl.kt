package com.jacksafblaze.storeowner.domain.usecaseimpl

import com.jacksafblaze.storeowner.domain.repository.LoginRepository
import com.jacksafblaze.storeowner.domain.usecase.CheckIfUserLoggedInUseCase
import kotlinx.coroutines.flow.Flow

class CheckIfUserLoggedInUseCaseImpl(private val repository: LoginRepository) : CheckIfUserLoggedInUseCase {
    override fun execute(): Flow<Boolean> {
        return repository.isUserLoggedIn()
    }
}