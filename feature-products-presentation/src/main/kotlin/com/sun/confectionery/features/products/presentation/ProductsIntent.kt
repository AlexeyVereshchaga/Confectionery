package com.sun.confectionery.features.products.presentation

import com.sun.confectionery.core.mvi.BaseIntent

sealed interface ProductsIntent : BaseIntent {
    object Load : ProductsIntent
    data class Select(val id: String): ProductsIntent
}