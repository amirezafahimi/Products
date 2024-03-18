package com.example.products.domain.repository

import com.example.products.domain.entity.Product

interface ProductRepository {
    suspend fun fetchProductsOnline(): List<Product>
    //suspend fun fetchProductsOffline(): List<ProductDomain>
}