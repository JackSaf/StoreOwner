package com.jacksafblaze.storeowner.data.repository

import com.google.android.gms.common.api.Response
import com.google.firebase.auth.FirebaseAuth
import com.jacksafblaze.storeowner.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepositoryImpl(private val firebaseAuth: FirebaseAuth): LoginRepository {
    override fun isUserLoggedIn() = firebaseAuth.currentUser != null

    override suspend fun login(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO){
            val result = firebaseAuth.signInWithEmailAndPassword(email, password)
            result.exception?.let {
                throw it.cause!!
            }
            result.isSuccessful
        }
    }

    override suspend fun register(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password)
            result.exception?.let {
                throw it.cause!!
            }
            result.isSuccessful
        }
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }
}