package com.sun.confectionery.features.products.presentation

import com.sun.confectionery.features.products.data.ProductsApi
import com.sun.confectionery.features.products.domain.ProductRepository
import com.sun.confectionery.features.products.domainimpl.ProductsRepositoryImpl
import io.ktor.client.HttpClient
import org.koin.dsl.module


val productsDataModule = module {
    single { ProductsApi(get<HttpClient>()) }
    single<ProductRepository> { ProductsRepositoryImpl(get(), get()) }
}