package com.example.products.data.source.remote

import com.example.products.data.model.Product
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : RemoteDataSource {
    override suspend fun fetchProducts(): List<Product> =
        apiService.getProductList()
}
interface RemoteDataSource {
    suspend fun fetchProducts(): List<Product>
}
