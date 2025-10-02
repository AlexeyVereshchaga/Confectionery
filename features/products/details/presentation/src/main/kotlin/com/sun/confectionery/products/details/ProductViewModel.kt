package com.sun.confectionery.products.details

import androidx.lifecycle.SavedStateHandle
import com.sun.confectionery.core.mvi.BaseViewModel
import com.sun.confectionery.core.navigation.NavigationEvent
import com.sun.confectionery.core.navigation.NavigationManager
import com.sun.confectionery.products.domain.usecase.GetProductUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductViewModel(
    private val navigationManager: NavigationManager,
    private val getProductUseCase: GetProductUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ProductState, ProductIntent, ProductEvent>(ProductState()) {

    private var productId: String? = null

    init {
        productId = savedStateHandle.get<String>("productId")
    }

    fun handleIntent(intent: ProductIntent) {
        when (intent) {
            ProductIntent.Load -> load()
            ProductIntent.Back -> launch {
                navigationManager.navigate(NavigationEvent.NavigateBack)
            }
        }
    }

    private fun load() {
        launch {
            setState { copy(isLoading = true) }
            productId?.let {
                val local = withContext(Dispatchers.IO) { getProductUseCase(it) }
                setState { copy(isLoading = false, product = local) }
            }
        }
    }
}