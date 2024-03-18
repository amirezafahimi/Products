package com.example.products.data.source.remote

import com.example.products.data.model.ProductResponse
import retrofit2.http.GET

interface ApiService {

    @GET(".")
    suspend fun getProductList(): List<ProductResponse>
}
