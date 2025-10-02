package com.sun.confectionery.auth.presentation

import com.sun.confectionery.auth.domain.usecase.LoginUseCase
import com.sun.confectionery.auth.domain.usecase.RegisterUseCase
import com.sun.confectionery.core.Outcome
import com.sun.confectionery.core.mvi.BaseViewModel
import com.sun.confectionery.core.navigation.NavigationEvent
import com.sun.confectionery.core.navigation.NavigationManager

class AuthViewModel(
    private val navigationManager: NavigationManager,
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : BaseViewModel<AuthState, AuthIntent, AuthEvent>(AuthState()) {

    fun handleIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.Login -> doLogin(intent.email, intent.password)
        }
    }

    private fun doLogin(email: String, password: String) {
        launch {
            setState { copy(isLoading = true) }
            val res = loginUseCase(email, password)
            setState { copy(isLoading = false) }
            when (res) {
                is Outcome.Error -> {
                    sendEvent(AuthEvent.Error(res.throwable.message ?: "Unknown error"))
                }

                Outcome.Loading -> {
                    setState { copy(isLoading = true) }
                }

                is Outcome.Success<*> -> {
                    sendEvent(AuthEvent.Success)
                    navigationManager.navigate(NavigationEvent.NavigateToProducts)
                }
            }
        }
    }
}