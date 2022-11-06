package com.jacksafblaze.storeowner.di.modules

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.jacksafblaze.storeowner.data.repository.CategoryRepositoryImpl
import com.jacksafblaze.storeowner.data.repository.LoginRepositoryImpl
import com.jacksafblaze.storeowner.data.repository.ProductRepositoryImpl
import com.jacksafblaze.storeowner.domain.repository.CategoryRepository
import com.jacksafblaze.storeowner.domain.repository.LoginRepository
import com.jacksafblaze.storeowner.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideLoginRepository(firebaseAuth: FirebaseAuth): LoginRepository {
        return LoginRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(
        firebaseAuth: FirebaseAuth,
        firebaseDatabase: FirebaseDatabase,
        firebaseCloudStorage: FirebaseStorage
    ): CategoryRepository {
        return CategoryRepositoryImpl(firebaseAuth, firebaseDatabase, firebaseCloudStorage)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        firebaseAuth: FirebaseAuth,
        firebaseDatabase: FirebaseDatabase,
        firebaseCloudStorage: FirebaseStorage
    ): ProductRepository {
        return ProductRepositoryImpl(firebaseAuth, firebaseDatabase, firebaseCloudStorage)
    }
}