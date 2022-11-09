package com.jacksafblaze.storeowner.domain.usecase.login

import kotlinx.coroutines.flow.Flow

interface CheckIfUserLoggedInUseCase {
    fun execute(): Boolean
}