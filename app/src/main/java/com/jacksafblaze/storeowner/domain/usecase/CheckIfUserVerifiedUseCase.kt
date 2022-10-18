package com.jacksafblaze.storeowner.domain.usecase

import kotlinx.coroutines.flow.Flow

interface CheckIfUserVerifiedUseCase {
    fun execute(): Flow<Boolean>
}