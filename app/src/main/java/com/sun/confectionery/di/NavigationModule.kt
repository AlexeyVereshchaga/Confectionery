package com.sun.confectionery.di

import com.sun.confectionery.core.navigation.NavigationManager
import org.koin.dsl.module

val navigationModule = module {
    single { NavigationManager() }
}