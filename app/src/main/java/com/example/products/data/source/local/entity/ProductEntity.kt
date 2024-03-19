package com.example.products.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.products.domain.entity.Product

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rate: Double,
    val ratingCount: Int
) {
    fun mapToProductDomain() = Product(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        image = image,
        rate = rate,
        ratingCount = ratingCount
    )
}