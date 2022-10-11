package com.jacksafblaze.storeowner.di.modules

import com.google.firebase.auth.FirebaseAuth
import com.jacksafblaze.storeowner.data.repository.LoginRepositoryImpl
import com.jacksafblaze.storeowner.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LoginRepositoryModule {
    @Provides
    @Singleton
    fun provideLoginRepository(firebaseAuth: FirebaseAuth): LoginRepository{
        return LoginRepositoryImpl(firebaseAuth)
    }
}