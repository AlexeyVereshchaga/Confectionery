package com.sun.confectionery.products.details

import androidx.lifecycle.SavedStateHandle
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val productViewModelModule = module {
    viewModel { (handle: SavedStateHandle) ->
        ProductViewModel(
            navigationManager = get(),
            getProductUseCase = get(),
            savedStateHandle = handle
        )
    }
}