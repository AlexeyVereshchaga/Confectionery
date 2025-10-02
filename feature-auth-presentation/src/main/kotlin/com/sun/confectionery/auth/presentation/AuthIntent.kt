package com.sun.confectionery.auth.presentation

import com.sun.confectionery.core.mvi.BaseIntent

sealed class AuthIntent : BaseIntent {
    data class Login(val email: String, val password: String) : AuthIntent()
}
