package com.jacksafblaze.storeowner.presentation.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacksafblaze.storeowner.domain.usecase.ViewProductsFromCategoryUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductListViewModel(private val viewProductsFromCategoryUseCase: ViewProductsFromCategoryUseCase): ViewModel() {
    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState = _uiState.asStateFlow()

    private var job: Job? = null
    init {
        viewProducts()
    }
    private fun viewProducts() {
        job?.cancel()
        job = viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            try {
                viewProductsFromCategoryUseCase.execute(categoryId = uiState.value.categoryId!!).collect{ products ->
                    _uiState.update {state ->
                        state.copy(products = products)
                    }
                }
            }
            catch (e: Exception){
                _uiState.update {
                    it.copy(message = e.message)
                }
            }
            _uiState.update {
                it.copy(isLoading = false)
            }
        }
    }
}