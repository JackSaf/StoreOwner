package com.jacksafblaze.storeowner.presentation.products

import androidx.lifecycle.ViewModel
import com.jacksafblaze.storeowner.domain.usecase.AddProductToCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(private val addProductToCategoryUseCase: AddProductToCategoryUseCase): ViewModel() {

}