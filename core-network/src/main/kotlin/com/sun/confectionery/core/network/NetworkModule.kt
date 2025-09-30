package com.sun.confectionery.core.network

import org.koin.dsl.module

val networkModule = module {
    single { AuthHttpClient(get()) } // Koin automatically provides TokenStorage via get()
}