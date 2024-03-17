package com.example.products.data.repository

import com.example.products.data.source.remote.RemoteDataSource
import com.example.products.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    //private val localDataSource: LocalDataSource
) : ProductRepository {

}
