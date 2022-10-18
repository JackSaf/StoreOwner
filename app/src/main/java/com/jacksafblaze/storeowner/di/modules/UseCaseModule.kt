package com.jacksafblaze.storeowner.di.modules

import com.jacksafblaze.storeowner.domain.repository.LoginRepository
import com.jacksafblaze.storeowner.domain.usecase.*
import com.jacksafblaze.storeowner.domain.usecaseimpl.*
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
    fun provideCheckIfLoggedInUseCase(repository: LoginRepository): CheckIfUserLoggedInUseCase {
        return CheckIfUserLoggedInUseCaseImpl(repository)
    }

    @Provides
    fun provideSendVerificationEmailUseCase(repository: LoginRepository): SendVerificationEmailUseCase {
        return SendVerificationEmailUseCaseImpl(repository)
    }

    @Provides
    fun provideLogoutUseCase(repository: LoginRepository): LogoutUseCase{
        return LogoutUseCaseImpl(repository)
    }

    @Provides
    fun provideCheckIfUserVerifiedUseCase(repository: LoginRepository): CheckIfUserVerifiedUseCase{
        return CheckIfUserVerifiedUseCaseImpl(repository)
    }
}