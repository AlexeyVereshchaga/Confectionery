package com.sun.confectionery.auth.presentation

import com.sun.confectionery.core.mvi.BaseEvent

sealed interface AuthEvent : BaseEvent {
    object Success : AuthEvent
    data class Error(val message: String) : AuthEvent
}