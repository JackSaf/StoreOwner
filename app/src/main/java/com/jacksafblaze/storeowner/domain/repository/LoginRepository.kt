package com.jacksafblaze.storeowner.domain.repository

import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun isUserLoggedIn(): Flow<Boolean>
    fun isUserVerified(): Flow<Boolean>
    suspend fun sendVerificationEmail(): Boolean
    suspend fun login(email: String, password: String): Boolean
    suspend fun register(email: String, password: String): Boolean
    fun logout()
}