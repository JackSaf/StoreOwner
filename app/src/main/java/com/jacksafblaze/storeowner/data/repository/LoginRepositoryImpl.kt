package com.jacksafblaze.storeowner.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.jacksafblaze.storeowner.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginRepositoryImpl(private val firebaseAuth: FirebaseAuth): LoginRepository {

    override fun isUserLoggedIn(): Boolean{
        return firebaseAuth.currentUser != null
    }

    override fun isUserVerified(): Boolean{
        return firebaseAuth.currentUser?.isEmailVerified ?: false
    }

    override suspend fun login(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO){
            val result = firebaseAuth.signInWithEmailAndPassword(email, password)
            result.await()
            result.isSuccessful
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
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password)
            result.await()
            result.isSuccessful
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}