package com.jacksafblaze.storeowner.presentation.products

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacksafblaze.storeowner.domain.model.Product
import com.jacksafblaze.storeowner.domain.usecase.products.AddProductToCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(private val addProductToCategoryUseCase: AddProductToCategoryUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow(AddProductUiState())
    val uiState = _uiState.asStateFlow()


    fun setImage(imageUri: Uri?){
        _uiState.update {
            it.copy(imageUri = imageUri)
        }
    }

    fun setName(name: String){
        _uiState.update {
            it.copy(name = name)
        }
    }

    fun setCost(cost: String){
        _uiState.update {
            it.copy(cost = cost)
        }
    }

    fun setPrice(price: String){
        _uiState.update {
            it.copy(price = price)
        }
    }

    fun setUnit(unit: String){
        _uiState.update {
            it.copy(unit = unit)
        }
    }

    fun addProduct() = viewModelScope.launch{
        val product = Product(
            "",
            uiState.value.name!!,
            uiState.value.cost!!.toDouble(),
            uiState.value.price!!.toDouble(),
            uiState.value.unit!!,
        )
        addProductToCategoryUseCase.execute(product, uiState.value.categoryId!!)
    }



}