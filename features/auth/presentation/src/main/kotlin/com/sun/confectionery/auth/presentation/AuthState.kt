package com.sun.confectionery.auth.presentation

import com.sun.confectionery.core.mvi.BaseState

data class AuthState(
    val isLoading: Boolean = false,
    val isAuthorized: Boolean = false
) : BaseState
