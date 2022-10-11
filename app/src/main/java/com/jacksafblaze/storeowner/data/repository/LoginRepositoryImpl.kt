package com.jacksafblaze.storeowner.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.jacksafblaze.storeowner.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepositoryImpl(val firebaseAuth: FirebaseAuth): LoginRepository {
    override suspend fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override suspend fun login(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO){
            firebaseAuth.signInWithEmailAndPassword(email, password).isSuccessful
        }
    }

    override suspend fun register(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            firebaseAuth.createUserWithEmailAndPassword(email, password).isSuccessful
        }
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }
}