package com.sun.confectionery.products.domain

import com.sun.confectionery.core.Outcome
import com.sun.confectionery.products.domain.model.Product

interface ProductsRepository {
    suspend fun refreshProductsFromNetwork(): Outcome<Unit>
    suspend fun getAllLocal(): List<Product>
    suspend fun getById(id: String): Product?
}