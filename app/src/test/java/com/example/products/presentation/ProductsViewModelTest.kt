package com.example.products.presentation

import app.cash.turbine.test
import com.example.products.domain.entity.Product
import com.example.products.domain.usecase.GetProductsOfflineUseCase
import com.example.products.domain.usecase.GetProductsOnlineUseCase
import com.example.products.ui.products.ProductsViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class ProductsViewModelTest {

    // Assuming Product is a data class with at least a 'title' property
    private val product1 =
        Product(
            id = 1,
            title = "T1",
            price = 125.4,
            description = "D1",
            category = "C1",
            image = "",
            rate = 1.1,
            ratingCount = 14
        )
    private val product2 =
        Product(
            id = 2,
            title = "T2",
            price = 225.4,
            description = "D2",
            category = "C2",
            image = "",
            rate = 2.1,
            ratingCount = 24
        )

    // Create mock objects for the dependencies of the ViewModel

    private var getProductsOnlineUseCase: GetProductsOnlineUseCase = mock()

    private var getProductsOfflineUseCase: GetProductsOfflineUseCase = mock()

    private lateinit var viewModel: ProductsViewModel

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        MockitoAnnotations.initMocks(this)

// Initialize your ViewModel here with test use cases
        viewModel = ProductsViewModel(
            getProductsOnlineUseCase,
            getProductsOfflineUseCase
        )
    }

    @Test
    fun `isLoading should be true when fetching products online`() = runTest {
        viewModel.getProductsOnline()
        viewModel.isLoading.test {
            assertEquals(true, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `isLoading should be false after products are fetched online`() = runTest {
        viewModel.getProductsOnline()
        viewModel.isLoading.test {
            awaitItem() // Skip the initial loading state
            assertEquals(false, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `filteredProducts should update when search query changes`() = runTest {
        whenever(getProductsOnlineUseCase()).thenReturn(listOf(product1, product2))
        viewModel.getProductsOnline()
        viewModel.searchProduct("1")
        viewModel.searchProduct("2")
        viewModel.filteredProducts.test {
            assertEquals(listOf(product1), awaitItem())
            assertEquals(listOf(product2), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `filteredProducts should return all products when query is blank`() = runTest {
        whenever(getProductsOnlineUseCase()).thenReturn(listOf(product1, product2))
        viewModel.getProductsOnline()
        viewModel.searchProduct("")
        viewModel.filteredProducts.test {
            assertEquals(listOf(product1, product2), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}