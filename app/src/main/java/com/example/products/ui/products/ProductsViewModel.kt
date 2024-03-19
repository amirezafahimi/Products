package com.example.products.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.products.domain.entity.Product
import com.example.products.domain.usecase.GetProductsOfflineUseCase
import com.example.products.domain.usecase.GetProductsOnlineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsOnlineUseCase: GetProductsOnlineUseCase,
    private val getProductsOfflineUseCase: GetProductsOfflineUseCase,
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    private val _searchQuery = MutableStateFlow("")

    private val _filteredProducts = MutableStateFlow<List<Product>>(emptyList())
    val filteredProducts: StateFlow<List<Product>> = _filteredProducts

    init {
        observeFilteredProducts()
    }

    fun getProductsOnline() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            getProductsOnlineUseCase().let {
                _isLoading.value = false
                _allProducts.value = it
            }
        }
    }
    fun getProductsOffline() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            getProductsOfflineUseCase().let {
                _isLoading.value = false
                _allProducts.value = it
            }
        }
    }

    private fun observeFilteredProducts() {
        viewModelScope.launch {
            combine(_allProducts, _searchQuery) { all, query ->
                filterProductsByQuery(all, query)
            }.collect { filtered ->
                _filteredProducts.value = filtered
            }
        }
    }

    fun searchProduct(query: String) {
        _searchQuery.value = query
    }

    private fun filterProductsByQuery(products: List<Product>, query: String): List<Product> {
        return if (query.isBlank()) {
            products
        } else {
            products.filter { it.title.contains(query, ignoreCase = true) }
        }
    }
}