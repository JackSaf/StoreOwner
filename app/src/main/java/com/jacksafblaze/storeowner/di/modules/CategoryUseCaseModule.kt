package com.jacksafblaze.storeowner.di.modules

import com.jacksafblaze.storeowner.domain.repository.CategoryRepository
import com.jacksafblaze.storeowner.domain.usecase.categories.AddCategoryUseCase
import com.jacksafblaze.storeowner.domain.usecase.categories.DeleteCategoryUseCase
import com.jacksafblaze.storeowner.domain.usecase.categories.UpdateCategoryUseCase
import com.jacksafblaze.storeowner.domain.usecase.categories.ViewCategoriesUseCase
import com.jacksafblaze.storeowner.domain.usecaseimpl.categories.AddCategoryUseCaseImpl
import com.jacksafblaze.storeowner.domain.usecaseimpl.categories.DeleteCategoryUseCaseImpl
import com.jacksafblaze.storeowner.domain.usecaseimpl.categories.UpdateCategoryUseCaseImpl
import com.jacksafblaze.storeowner.domain.usecaseimpl.categories.ViewCategoriesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class CategoryUseCaseModule {
    @Provides
    fun provideViewCategoriesUseCase(repository: CategoryRepository): ViewCategoriesUseCase {
        return ViewCategoriesUseCaseImpl(repository)
    }
    @Provides
    fun provideAddCategoryUseCase(repository: CategoryRepository): AddCategoryUseCase {
        return AddCategoryUseCaseImpl(repository)
    }
    @Provides
    fun provideUpdateCategoryUseCase(repository: CategoryRepository): UpdateCategoryUseCase {
        return UpdateCategoryUseCaseImpl(repository)
    }
    @Provides
    fun provideDeleteCategoryUseCase(repository: CategoryRepository): DeleteCategoryUseCase {
        return DeleteCategoryUseCaseImpl(repository)
    }
}