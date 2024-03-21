package com.example.products.data

import com.example.products.createMockProductsResponse
import com.example.products.data.model.ProductResponse
import com.example.products.data.source.remote.ApiService
import com.example.products.data.source.remote.RemoteDataSourceImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class RemoteDataSourceTest {

    @Mock
    private lateinit var apiService: ApiService

    @InjectMocks
    private lateinit var remoteDataSource: RemoteDataSourceImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `fetchProducts returns empty list on API error`() = runBlocking {
        // Mock API service response
        `when`(apiService.getProductList()).thenThrow(RuntimeException("API Error"))

        // Call the function under test
        val result = remoteDataSource.fetchProducts()

        // Verify that the result is an empty list
        assertEquals(emptyList<ProductResponse>(), result)
    }

    @Test
    fun `fetchProducts returns product list on successful API call`() = runBlocking {
        // Mock API service response
        val expectedProducts = createMockProductsResponse()
        `when`(apiService.getProductList()).thenReturn(expectedProducts)

        // Call the function under test
        val result = remoteDataSource.fetchProducts()

        // Verify that the result matches the expected product list
        assertEquals(expectedProducts, result)
    }
}
