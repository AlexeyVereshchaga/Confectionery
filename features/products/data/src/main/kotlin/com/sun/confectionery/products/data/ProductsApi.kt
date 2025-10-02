package com.sun.confectionery.products.data

interface ProductsApi {
    suspend fun fetchAll(): List<ProductDtoRemote>
}