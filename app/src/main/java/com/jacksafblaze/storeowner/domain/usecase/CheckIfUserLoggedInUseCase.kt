package com.jacksafblaze.storeowner.domain.usecase

import kotlinx.coroutines.flow.Flow

interface CheckIfUserLoggedInUseCase {
    fun execute(): Flow<Boolean>
}