package com.sun.confectionery.features.products.domain

import com.sun.confectionery.core.Outcome


interface ProductRepository {
    suspend fun refreshProductsFromNetwork(): Outcome<Unit>
    suspend fun getAllLocal(): List<ProductModel>
    suspend fun getById(id: String): ProductModel?
}
