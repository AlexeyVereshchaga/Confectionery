package com.sun.confectionery.features.products.presentation

import com.sun.confectionery.products.data.ProductsApi
import com.sun.confectionery.products.domain.ProductsRepository
import com.sun.confectionery.features.products.domainimpl.ProductsRepositoryImpl
import com.sun.confectionery.products.data.ProductsApiImpl
import com.sun.confectionery.products.domain.usecase.GetProductsUseCase
import com.sun.confectionery.products.domain.usecase.RefreshProductsUseCase
import io.ktor.client.HttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val productsDataModule = module {
    single<ProductsApi> { ProductsApiImpl(client = get()) }
}

val productsDomainModule = module {
    single<ProductsRepository> {
        ProductsRepositoryImpl(
            api = get(),
            dao = get()
        )
    }
    factory { GetProductsUseCase(get()) }
    factory { RefreshProductsUseCase(get()) }
}

val productsViewModelModule = module {
    viewModel {
        ProductsViewModel(
            navigationManager = get(),
            refreshProductsUseCase = get(),
            getProductsUseCase = get()
        )
    }
}

val productsModules = listOf(
    productsDataModule,
    productsDomainModule,
    productsViewModelModule,
)