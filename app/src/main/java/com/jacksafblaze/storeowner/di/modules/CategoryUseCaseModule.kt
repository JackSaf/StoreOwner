package com.jacksafblaze.storeowner.di.modules

import com.jacksafblaze.storeowner.domain.repository.CategoryRepository
import com.jacksafblaze.storeowner.domain.usecase.AddCategoryUseCase
import com.jacksafblaze.storeowner.domain.usecase.DeleteCategoryUseCase
import com.jacksafblaze.storeowner.domain.usecase.UpdateCategoryUseCase
import com.jacksafblaze.storeowner.domain.usecase.ViewCategoriesUseCase
import com.jacksafblaze.storeowner.domain.usecaseimpl.AddCategoryUseCaseImpl
import com.jacksafblaze.storeowner.domain.usecaseimpl.DeleteCategoryUseCaseImpl
import com.jacksafblaze.storeowner.domain.usecaseimpl.UpdateCategoryUseCaseImpl
import com.jacksafblaze.storeowner.domain.usecaseimpl.ViewCategoriesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class CategoryUseCaseModule {
    @Provides
    fun provideViewCategoriesUseCase(repository: CategoryRepository): ViewCategoriesUseCase{
        return ViewCategoriesUseCaseImpl(repository)
    }
    @Provides
    fun provideAddCategoryUseCase(repository: CategoryRepository): AddCategoryUseCase{
        return AddCategoryUseCaseImpl(repository)
    }
    @Provides
    fun provideUpdateCategoryUseCase(repository: CategoryRepository): UpdateCategoryUseCase{
        return UpdateCategoryUseCaseImpl(repository)
    }
    @Provides
    fun provideDeleteCategoryUseCase(repository: CategoryRepository): DeleteCategoryUseCase{
        return DeleteCategoryUseCaseImpl(repository)
    }
}