package com.example.products

import com.example.products.data.model.ProductResponse
import com.example.products.data.model.Rating
import com.example.products.data.source.local.entity.ProductEntity

fun createMockProductsResponse(): List<ProductResponse> {
    return listOf(
        ProductResponse(
            id = 1,
            title = "T1",
            price = 125.4,
            description = "D1",
            category = "C1",
            image = "",
            rating = Rating(1.1, 14)
        ),
        ProductResponse(
            id = 2,
            title = "T2",
            price = 225.2,
            description = "D2",
            category = "C2",
            image = "",
            rating = Rating(2.1, 24)
        )
    )
}

fun createMockProductEntities(): List<ProductEntity> {
    return listOf(
        ProductEntity(
            id = 1,
            title = "T1",
            price = 125.4,
            description = "D1",
            category = "C1",
            image = "",
            rate = 1.1,
            ratingCount = 14
        ),
        ProductEntity(
            id = 2,
            title = "T2",
            price = 225.2,
            description = "D2",
            category = "C2",
            image = "",
            rate = 2.1,
            ratingCount = 24
        )
    )
}