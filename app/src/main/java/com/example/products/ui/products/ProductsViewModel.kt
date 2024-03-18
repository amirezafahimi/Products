package com.example.products.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.products.domain.entity.Product
import com.example.products.domain.usecase.GetProductsOnlineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsOnlineUseCase: GetProductsOnlineUseCase,
) : ViewModel() {

    // StateFlow to hold the UI state (loading, error, success)
    private val _uiState = MutableStateFlow(ProductsState())
    val uiState: StateFlow<ProductsState> = _uiState


    fun getProductsOnline() {
        _uiState.value = uiState.value.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            getProductsOnlineUseCase().let {
                _uiState.value = uiState.value.copy(
                    isLoading = false,
                    success = it,
                )
            }
        }
    }
}

data class ProductsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: List<Product> = emptyList(),
)
