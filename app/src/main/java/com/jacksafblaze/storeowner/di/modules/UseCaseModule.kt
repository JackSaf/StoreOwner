package com.jacksafblaze.storeowner.di.modules

import com.jacksafblaze.storeowner.domain.repository.LoginRepository
import com.jacksafblaze.storeowner.domain.usecase.CheckIfLoggedInUseCase
import com.jacksafblaze.storeowner.domain.usecase.LoginUseCase
import com.jacksafblaze.storeowner.domain.usecase.RegisterUseCase
import com.jacksafblaze.storeowner.domain.usecaseimpl.CheckIfLoggedInUseCaseImpl
import com.jacksafblaze.storeowner.domain.usecaseimpl.LoginUseCaseImpl
import com.jacksafblaze.storeowner.domain.usecaseimpl.RegisterUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun provideLoginUseCase(repository: LoginRepository): LoginUseCase {
        return LoginUseCaseImpl(repository)
    }

    @Provides
    fun provideRegisterUseCase(repository: LoginRepository): RegisterUseCase{
        return RegisterUseCaseImpl(repository)
    }

    @Provides
    fun provideCheckIfLoggedInUseCase(repository: LoginRepository): CheckIfLoggedInUseCase{
        return CheckIfLoggedInUseCaseImpl(repository)
    }

    @Provides
    fun provideSendVerificationEmailUseCase(repository: LoginRepository): SendVerificationEmailUseCase{
        return SendVerificationEmailUseCaseImpl(repository)
    }

}