package com.jacksafblaze.storeowner.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.jacksafblaze.storeowner.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginRepositoryImpl(private val firebaseAuth: FirebaseAuth): LoginRepository {
    override fun isUserLoggedIn() = firebaseAuth.currentUser != null

    override suspend fun login(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO){
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            true
        }
    }

    override suspend fun sendVerificationEmail(): Boolean{
        return withContext(Dispatchers.IO){
            if(firebaseAuth.currentUser != null) {
                val result = firebaseAuth.currentUser!!.sendEmailVerification()
                result.await()
                result.isSuccessful
            }
            else{
                false
            }
        }
    }
    override suspend fun register(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            true
        }
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }
}