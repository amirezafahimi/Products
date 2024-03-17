package com.example.products.data.source.remote

import com.example.products.data.model.Product
import retrofit2.http.GET

interface ApiService {

    @GET(".")
    suspend fun getProductList(): List<Product>
}
