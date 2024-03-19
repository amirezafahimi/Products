package com.example.products.data.repository

import com.example.products.data.source.local.LocalDataSource
import com.example.products.data.source.local.entity.ProductEntity
import com.example.products.data.source.remote.RemoteDataSource
import com.example.products.domain.entity.Product
import com.example.products.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : ProductRepository {
    override suspend fun fetchProductsOnline(): List<Product> {
        return remoteDataSource.fetchProducts().map {
            it.mapToProductDomain()
        }.also { products ->
            localDataSource.storeProducts(
                products.map { product ->
                    ProductEntity(
                        id = product.id,
                        title = product.title,
                        price = product.price,
                        description = product.description,
                        category = product.category,
                        image = product.image,
                        rate = product.rate,
                        ratingCount = product.id,
                    )
                }
            )
        }
    }

    override suspend fun fetchProductsOffline(): List<Product> {
        return localDataSource.getLastProducts().map {
            it.mapToProductDomain()
        }
    }
}
