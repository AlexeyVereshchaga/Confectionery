package com.sun.confectionery.di


import com.sun.confectionery.core.storage.TokenStorage
import com.sun.confectionery.storage.EncryptedTokenStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val authModule = module {
    single<TokenStorage> { EncryptedTokenStorage(androidContext()) }
}
