package com.sun.confectionery.core.navigation

sealed class NavigationEvent {
    object NavigateToProducts : NavigationEvent()
    data class NavigateToProductDetail(val productId: String) : NavigationEvent()
    object NavigateBack : NavigationEvent()
}