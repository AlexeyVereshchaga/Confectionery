package com.sun.confectionery

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.util.DebugLogger
import com.sun.confectionery.di.authModule
import com.sun.confectionery.di.navigationModule
import com.sun.confectionery.auth.presentation.authModules
import com.sun.confectionery.core.db.dbModule
import com.sun.confectionery.core.network.networkModule
import com.sun.confectionery.features.products.presentation.productsModules
import com.sun.confectionery.products.details.productViewModelModule
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        val modules = mutableListOf(
            authModule,
            networkModule,
            navigationModule,
            dbModule,
            productViewModelModule
        )
        modules.addAll(authModules)
        modules.addAll(productsModules)
        GlobalContext.startKoin {
            androidContext(this@MainApplication)
            modules(modules)
        }
    }
}