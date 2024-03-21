package com.example.products.data

import com.example.products.createMockProductEntities
import com.example.products.createMockProductsResponse
import com.example.products.data.repository.ProductRepositoryImpl
import com.example.products.data.source.local.LocalDataSource
import com.example.products.data.source.remote.RemoteDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class SearchImageRepositoryTest {

    @Mock
    private lateinit var mockRemoteDataSource: RemoteDataSource

    @Mock
    private lateinit var mockLocalDataSource: LocalDataSource

    @InjectMocks
    private lateinit var searchImageRepository: ProductRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `fetchDataByQuery success`() = runBlocking {

        val response = createMockProductsResponse()

        `when`(mockRemoteDataSource.fetchProducts()).thenReturn(response)

        val result = searchImageRepository.fetchProductsOnline()
        val expected = response.map { it.mapToProductDomain() }

        assertEquals(expected.size, result.size)
        assertEquals(expected[0].id, result[0].id)
    }

    @Test
    fun `getOfflineInitialData with data`() = runBlocking {

        val productEntities = createMockProductEntities()
        `when`(mockLocalDataSource.getLastProducts()).thenReturn(productEntities)

        val result = searchImageRepository.fetchProductsOffline()
        val expected = productEntities.map { it.mapToProductDomain() }

        assertEquals(expected.size, result.size)
        assertEquals(expected[0].id, result[0].id)
    }
}
