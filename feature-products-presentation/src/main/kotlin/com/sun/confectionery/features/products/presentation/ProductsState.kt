package com.sun.confectionery.features.products.presentation

import com.sun.confectionery.core.mvi.BaseState
import com.sun.confectionery.features.products.domain.ProductModel


data class ProductsState(
    val isLoading: Boolean = false,
    val items: List<ProductModel> = emptyList()
): BaseState

