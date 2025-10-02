package com.sun.confectionery.products.data

import com.sun.confectionery.core.network.AuthHttpClient
import com.sun.confectionery.core.network.startRequest
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ProductsApiImpl(private val client: AuthHttpClient) : ProductsApi {
    override suspend fun fetchAll(): List<ProductDtoRemote> {
        return client.client.startRequest {
            get("/products") {
                contentType(ContentType.Application.Json)
            }
        }
    }
}