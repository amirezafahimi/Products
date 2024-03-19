package com.example.products.data.source.local

import com.example.findpix.data.source.local.AppDao
import com.example.products.data.source.local.entity.ProductEntity
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val appDao: AppDao
) : LocalDataSource {

    override suspend fun getLastProducts(): List<ProductEntity> = appDao.getLastProducts() ?: listOf()

    override suspend fun storeProducts(products: List<ProductEntity>) {
        appDao.insertAndClear(products)
    }
}

interface LocalDataSource {

    suspend fun getLastProducts(): List<ProductEntity>

    suspend fun storeProducts(products: List<ProductEntity>)
}
