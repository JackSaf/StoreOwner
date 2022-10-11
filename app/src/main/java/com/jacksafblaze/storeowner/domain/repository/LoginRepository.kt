package com.jacksafblaze.storeowner.domain.repository

interface LoginRepository {
    suspend fun isUserLoggedIn(): Boolean
    suspend fun login(email: String, password: String): Boolean
    suspend fun register(email: String, password: String): Boolean
    suspend fun logout()
}