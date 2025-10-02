package com.sun.confectionery.auth.presentation

import com.sun.confectionery.auth.data.AuthApi
import com.sun.confectionery.auth.data.AuthApiImpl
import com.sun.confectionery.auth.domain.AuthRepository
import com.sun.confectionery.auth.domain.usecase.LoginUseCase
import com.sun.confectionery.auth.domain.usecase.RegisterUseCase
import com.sun.confectionery.auth.domainimpl.AuthRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authDataModule = module {
    single<AuthApi> { AuthApiImpl(client = get()) }
}

val authDomainModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(
            api = get(),
            tokenStorage = get()
        )
    }
    factory { LoginUseCase(get()) }
    factory { RegisterUseCase(get()) }
}

val authViewModelModule = module {
    viewModel {
        AuthViewModel(
            navigationManager = get(),
            loginUseCase = get(),
            registerUseCase = get()
        )
    }
}

val authModules = listOf(
    authViewModelModule,
    authDomainModule,
    authDataModule,
)