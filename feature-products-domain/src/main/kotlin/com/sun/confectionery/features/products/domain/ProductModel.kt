package com.sun.confectionery.features.products.domain


data class ProductModel(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val formattedPrice: String,
    val imageUrl: String
)

