package com.sun.confectionery.features.products.data


import io.ktor.client.*
import io.ktor.client.request.*

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

class ProductsApi(private val client: HttpClient) {
    suspend fun fetchAll(): List<ProductDtoRemote> {
        return client.get("${ApiConfig.BASE_URL}/products")
    }
}

