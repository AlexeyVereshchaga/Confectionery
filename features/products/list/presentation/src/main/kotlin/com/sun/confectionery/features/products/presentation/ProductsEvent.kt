package com.sun.confectionery.features.products.presentation

import com.sun.confectionery.core.mvi.BaseEvent


sealed interface ProductsEvent : BaseEvent {
    data class OpenProduct(val id: String) : ProductsEvent
}
