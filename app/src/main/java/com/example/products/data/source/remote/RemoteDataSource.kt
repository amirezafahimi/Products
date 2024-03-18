package com.example.products.data.source.remote

import com.example.products.data.model.ProductResponse
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : RemoteDataSource {
    override suspend fun fetchProducts(): List<ProductResponse> =
        try {
            apiService.getProductList()
        } catch (e: Exception) {
            listOf()
        }
}

interface RemoteDataSource {
    suspend fun fetchProducts(): List<ProductResponse>
}
