package com.sun.confectionery.core.navigation

sealed class Destinations(val route: String) {
    object Login : Destinations("login")
    object Products : Destinations("products")
    data class ProductDetail(val productId: String) : Destinations("product_detail/{productId}") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }
}