package com.jacksafblaze.storeowner.domain.usecaseimpl

import com.jacksafblaze.storeowner.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow

class CheckIfUserVerifiedUseCaseImpl(private val repository: LoginRepository) {
    fun execute(): Flow<Boolean> {
        return repository.isUserVerified()
    }
}