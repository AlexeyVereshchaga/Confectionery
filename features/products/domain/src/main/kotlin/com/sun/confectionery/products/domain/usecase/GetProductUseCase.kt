package com.sun.confectionery.products.domain.usecase

import com.sun.confectionery.products.domain.ProductsRepository

class GetProductUseCase(private val repository: ProductsRepository) {
    suspend operator fun invoke(id: String) = repository.getById(id)
}