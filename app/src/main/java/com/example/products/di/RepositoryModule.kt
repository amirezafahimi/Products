package com.example.products.di

import com.example.products.data.repository.ProductRepositoryImpl
import com.example.products.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindSearchImageRepository(searchImageRepositoryImpl: ProductRepositoryImpl): ProductRepository
}
