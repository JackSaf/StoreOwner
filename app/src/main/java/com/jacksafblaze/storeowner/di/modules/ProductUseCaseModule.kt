package com.jacksafblaze.storeowner.di.modules


import com.jacksafblaze.storeowner.domain.repository.ProductRepository
import com.jacksafblaze.storeowner.domain.usecase.AddProductToCategoryUseCase
import com.jacksafblaze.storeowner.domain.usecase.DeleteProductFromCategoryUseCase
import com.jacksafblaze.storeowner.domain.usecase.UpdateProductInCategoryUseCase
import com.jacksafblaze.storeowner.domain.usecase.ViewProductsFromCategoryUseCase
import com.jacksafblaze.storeowner.domain.usecaseimpl.AddProductToCategoryUseCaseImpl
import com.jacksafblaze.storeowner.domain.usecaseimpl.DeleteProductFromCategoryUseCaseImpl
import com.jacksafblaze.storeowner.domain.usecaseimpl.UpdateProductInCategoryUseCaseImpl
import com.jacksafblaze.storeowner.domain.usecaseimpl.ViewProductsFromCategoryUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ProductUseCaseModule {
    @Provides
    fun provideViewProductsFromCategoryUseCase(repository: ProductRepository): ViewProductsFromCategoryUseCase{
        return ViewProductsFromCategoryUseCaseImpl(repository)
    }
    @Provides
    fun provideAddProductToCategoryUseCase(repository: ProductRepository): AddProductToCategoryUseCase{
        return AddProductToCategoryUseCaseImpl(repository)
    }
    @Provides
    fun provideUpdateProductInCategoryUseCase(repository: ProductRepository): UpdateProductInCategoryUseCase{
        return UpdateProductInCategoryUseCaseImpl(repository)
    }
    @Provides
    fun provideDeleteProductFromCategoryUseCase(repository: ProductRepository): DeleteProductFromCategoryUseCase{
        return DeleteProductFromCategoryUseCaseImpl(repository)
    }
}