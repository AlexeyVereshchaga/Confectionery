package com.sun.confectionery.products.details

import com.sun.confectionery.core.mvi.BaseState
import com.sun.confectionery.products.domain.model.Product

data class ProductState(
    val isLoading: Boolean = false,
    val product: Product? = null,
) : BaseState
