package com.sun.confectionery.products.domain.model

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val formattedPrice: String,
    val imageUrl: String
)