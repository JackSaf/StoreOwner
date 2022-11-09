package com.jacksafblaze.storeowner.domain.usecase.login

import kotlinx.coroutines.flow.Flow

interface CheckIfUserVerifiedUseCase {
    fun execute(): Boolean
}