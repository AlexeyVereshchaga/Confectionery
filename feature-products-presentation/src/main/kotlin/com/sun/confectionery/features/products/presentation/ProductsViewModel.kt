package com.sun.confectionery.features.products.presentation


import com.sun.confectionery.core.Outcome
import com.sun.confectionery.core.auth.TokenStorage
import com.sun.confectionery.core.mvi.BaseViewModel
import com.sun.confectionery.features.products.domain.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductsViewModel(
    private val repo: ProductRepository
) : BaseViewModel<ProductsState, ProductsIntent, ProductsEvent>(ProductsState()) {

    fun handle(intent: ProductsIntent) {
        when (intent) {
            ProductsIntent.Load -> load()
            is ProductsIntent.Select -> sendEvent(ProductsEvent.OpenProduct(intent.id))
        }
    }

    private fun load(){
        launch {
            setState { copy(isLoading = true) }
            val res = repo.refreshProductsFromNetwork()
            if (res is Outcome.Success) {
                val local = withContext(Dispatchers.IO) { repo.getAllLocal() }
                setState { copy(isLoading = false, items = local) }
            } else {
                val local = withContext(Dispatchers.IO) { repo.getAllLocal() }
                setState { copy(isLoading = false, items = local) }
            }
        }
    }
}
