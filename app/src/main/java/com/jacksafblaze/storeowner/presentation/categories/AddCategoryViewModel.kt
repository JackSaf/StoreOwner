package com.jacksafblaze.storeowner.presentation.categories

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacksafblaze.storeowner.domain.model.Category
import com.jacksafblaze.storeowner.domain.usecase.categories.AddCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModel @Inject constructor(private val addCategoryUseCase: AddCategoryUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(AddCategoryUiState())
    val uiState = _uiState.asStateFlow()

    fun setName(name: String?){
        if(name.isNullOrBlank()){
            _uiState.update {
                it.copy(nameAlert = "Please enter the name")
            }
        }
        else{
            _uiState.update {
                it.copy(name = name, nameAlert = null)
            }
        }
    }
    fun setImage(imageUri: Uri?){
        _uiState.update {
            it.copy(imageUri = imageUri)
        }
    }
    fun userMessageShown(){
        _uiState.update {
            it.copy(message = null)
        }
    }
    fun addCategory() = viewModelScope.launch{
        val category = Category(name = uiState.value.name!!, imageUri = uiState.value.imageUri.toString())
        _uiState.update {
            it.copy(isLoading = true)
        }
        try {
            if(addCategoryUseCase.execute(category)){
                _uiState.update {
                    it.copy(isCategoryAdded = true)
                }
            }
            else{
                _uiState.update {
                    it.copy(isCategoryAdded = false, message = "Something went wrong")
                }
            }
        }
        catch (e: Exception){
            _uiState.update {
                it.copy(isCategoryAdded = false, message = e.message)
            }
        }
        finally {
            _uiState.update {
                it.copy(isLoading = false)
            }
        }
    }
}