package com.jacksafblaze.storeowner.presentation.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacksafblaze.storeowner.domain.usecase.products.ViewProductsFromCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(private val viewProductsFromCategoryUseCase: ViewProductsFromCategoryUseCase): ViewModel() {
    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState = _uiState.asStateFlow()

    private var job: Job? = null
    fun viewProductsFromCategory(categoryId: String) {
        _uiState.update {
            it.copy(categoryId = categoryId)
        }
        job?.cancel()
        job = viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            try {
                viewProductsFromCategoryUseCase.execute(categoryId = uiState.value.categoryId!!).collect{ products ->
                    _uiState.update {state ->
                        state.copy(products = products, isLoading = false)
                    }
                }
            }
            catch (e: Exception){
                _uiState.update {
                    it.copy(message = e.message, isLoading = false)
                }
            }
        }
    }
}