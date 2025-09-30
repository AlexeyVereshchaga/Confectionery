package com.sun.confectionery.app

import android.app.Application
import com.sun.confectionery.app.di.authModule
import com.sun.confectionery.core.network.networkModule
import com.sun.confectionery.features.products.presentation.productsDataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        val modules = listOf(
            authModule,
            networkModule,
            dbModule,
            authDataModule,
            productsDataModule,
            productsPresentationModule,
            authPresentationModule
        )
        GlobalContext.startKoin {
            androidContext(this@MainApplication)
            modules(modules)
        }
    }
}