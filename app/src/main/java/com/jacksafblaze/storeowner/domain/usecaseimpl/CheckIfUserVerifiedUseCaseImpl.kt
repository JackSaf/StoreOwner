package com.jacksafblaze.storeowner.domain.usecaseimpl

import com.jacksafblaze.storeowner.domain.repository.LoginRepository
import com.jacksafblaze.storeowner.domain.usecase.CheckIfUserVerifiedUseCase
import kotlinx.coroutines.flow.Flow

class CheckIfUserVerifiedUseCaseImpl(private val repository: LoginRepository): CheckIfUserVerifiedUseCase {
    override fun execute(): Flow<Boolean> {
        return repository.isUserVerified()
    }
}