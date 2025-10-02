package com.sun.confectionery.products.details

import com.sun.confectionery.core.mvi.BaseIntent

sealed interface ProductIntent : BaseIntent {
    object Load : ProductIntent
    object Back : ProductIntent
}
