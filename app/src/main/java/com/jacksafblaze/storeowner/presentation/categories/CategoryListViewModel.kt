package com.jacksafblaze.storeowner.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacksafblaze.storeowner.domain.usecase.categories.ViewCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryListViewModel @Inject constructor(
    private val viewCategoriesUseCase: ViewCategoriesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CategoryListUiState())
    val uiState = _uiState.asStateFlow()
    private var job: Job? = null

    init {
        viewCategories()
    }

    private fun viewCategories() {
        job?.cancel()
        job = viewModelScope.launch {
            _uiState.update {uiState ->
                uiState.copy(isLoading = true)
            }
            try {
                viewCategoriesUseCase.execute().collectLatest {categories ->
                    _uiState.update {uiState ->
                        uiState.copy(categories = categories)
                    }
                }
            }
            catch (e: Exception){
                _uiState.update {uiState ->
                    uiState.copy(message = e.message)
                }
            }
            finally {
                _uiState.update {uiState ->
                    uiState.copy(isLoading = false)
                }
            }

        }
    }

    fun userMessageShown(){
        _uiState.update {uiState ->
            uiState.copy(message = null)
        }
    }
}