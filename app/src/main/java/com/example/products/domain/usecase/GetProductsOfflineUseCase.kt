package com.example.products.domain.usecase

import com.example.products.domain.repository.ProductRepository
import javax.inject.Inject
import javax.inject.Singleton

class GetProductsOfflineUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke() = productRepository.fetchProductsOffline()
}
