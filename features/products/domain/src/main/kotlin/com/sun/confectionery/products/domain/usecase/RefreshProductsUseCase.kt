package com.sun.confectionery.products.domain.usecase

import com.sun.confectionery.products.domain.ProductsRepository

class RefreshProductsUseCase(private val repository: ProductsRepository) {
    suspend operator fun invoke() = repository.refreshProductsFromNetwork()
}