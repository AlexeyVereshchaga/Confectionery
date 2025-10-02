package com.sun.confectionery.features.products.presentation


import com.sun.confectionery.core.Outcome
import com.sun.confectionery.core.mvi.BaseViewModel
import com.sun.confectionery.core.navigation.NavigationEvent
import com.sun.confectionery.core.navigation.NavigationManager
import com.sun.confectionery.products.domain.usecase.GetProductsUseCase
import com.sun.confectionery.products.domain.usecase.RefreshProductsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductsViewModel(
    private val navigationManager: NavigationManager,
    private val refreshProductsUseCase: RefreshProductsUseCase,
    private val getProductsUseCase: GetProductsUseCase
) : BaseViewModel<ProductsState, ProductsIntent, ProductsEvent>(ProductsState()) {

    fun handleIntent(intent: ProductsIntent) {
        when (intent) {
            ProductsIntent.Load -> load()
            is ProductsIntent.Select -> {
                //sendEvent(ProductsEvent.OpenProduct(intent.id))
                launch {
                    navigationManager.navigate(NavigationEvent.NavigateToProductDetail(intent.id))
                }
            }
        }
    }

    private fun load() {
        launch {
            setState { copy(isLoading = true) }
            val res = refreshProductsUseCase()
            when (res) {
                is Outcome.Error, is Outcome.Success<*> -> {
                    val local = withContext(Dispatchers.IO) { getProductsUseCase() }
                    setState {
                        copy(
                            isLoading = false,
                            items = listOf(local, local, local).flatten()
                        )
                    }
                }

                Outcome.Loading -> setState { copy(isLoading = true) }
            }
        }
    }
}
