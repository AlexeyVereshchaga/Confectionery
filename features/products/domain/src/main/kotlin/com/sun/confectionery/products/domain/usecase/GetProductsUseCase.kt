package com.sun.confectionery.products.domain.usecase

import com.sun.confectionery.products.domain.ProductsRepository

class GetProductsUseCase(private val repository: ProductsRepository) {
    suspend operator fun invoke() = repository.getAllLocal()

}