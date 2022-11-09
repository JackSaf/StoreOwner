package com.jacksafblaze.storeowner.di.modules


import com.jacksafblaze.storeowner.domain.repository.ProductRepository
import com.jacksafblaze.storeowner.domain.usecase.products.AddProductToCategoryUseCase
import com.jacksafblaze.storeowner.domain.usecase.products.DeleteProductFromCategoryUseCase
import com.jacksafblaze.storeowner.domain.usecase.products.UpdateProductInCategoryUseCase
import com.jacksafblaze.storeowner.domain.usecase.products.ViewProductsFromCategoryUseCase
import com.jacksafblaze.storeowner.domain.usecaseimpl.products.AddProductToCategoryUseCaseImpl
import com.jacksafblaze.storeowner.domain.usecaseimpl.products.DeleteProductFromCategoryUseCaseImpl
import com.jacksafblaze.storeowner.domain.usecaseimpl.products.UpdateProductInCategoryUseCaseImpl
import com.jacksafblaze.storeowner.domain.usecaseimpl.products.ViewProductsFromCategoryUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ProductUseCaseModule {
    @Provides
    fun provideViewProductsFromCategoryUseCase(repository: ProductRepository): ViewProductsFromCategoryUseCase {
        return ViewProductsFromCategoryUseCaseImpl(repository)
    }
    @Provides
    fun provideAddProductToCategoryUseCase(repository: ProductRepository): AddProductToCategoryUseCase {
        return AddProductToCategoryUseCaseImpl(repository)
    }
    @Provides
    fun provideUpdateProductInCategoryUseCase(repository: ProductRepository): UpdateProductInCategoryUseCase {
        return UpdateProductInCategoryUseCaseImpl(repository)
    }
    @Provides
    fun provideDeleteProductFromCategoryUseCase(repository: ProductRepository): DeleteProductFromCategoryUseCase {
        return DeleteProductFromCategoryUseCaseImpl(repository)
    }
}