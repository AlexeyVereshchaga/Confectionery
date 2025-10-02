package com.sun.confectionery.features.products.presentation

import app.cash.turbine.test
import com.sun.confectionery.core.Outcome
import com.sun.confectionery.core.navigation.NavigationManager
import com.sun.confectionery.features.products.presentation.util.MainCoroutineRule
import com.sun.confectionery.products.domain.model.Product
import com.sun.confectionery.products.domain.usecase.GetProductsUseCase
import com.sun.confectionery.products.domain.usecase.RefreshProductsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductsViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ProductsViewModel
    private val navigationManager: NavigationManager = mockk(relaxed = true)
    private val refreshProductsUseCase: RefreshProductsUseCase = mockk()
    private val getProductsUseCase: GetProductsUseCase = mockk()

    @Before
    fun setUp() {
        viewModel = ProductsViewModel(navigationManager, refreshProductsUseCase, getProductsUseCase)
    }

    @Test
    fun `initial state is correct`() = runTest {
        val initialState = ProductsState()
        assertEquals(initialState, viewModel.state.value)
    }
}
