package com.sun.confectionery.di

import com.sun.confectionery.auth.EncryptedTokenStorage
import com.sun.confectionery.core.auth.TokenStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val authModule = module {
    single<TokenStorage> { EncryptedTokenStorage(androidContext()) }
}
