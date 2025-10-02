package com.sun.confectionery.features.products.presentation

import com.sun.confectionery.core.mvi.BaseState
import com.sun.confectionery.products.domain.model.Product


data class ProductsState(
    val isLoading: Boolean = true,
    val items: List<Product> = emptyList()
): BaseState

