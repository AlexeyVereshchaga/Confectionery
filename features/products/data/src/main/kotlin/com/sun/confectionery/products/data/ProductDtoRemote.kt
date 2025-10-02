package com.sun.confectionery.products.data

import kotlinx.serialization.Serializable

@Serializable
data class ProductDtoRemote(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val formattedPrice: String,
    val imageUrl: String
)
