package com.sun.confectionery.auth.data.dto

data class AuthDto(
    val email: String,
    val password: String,
    val isAdmin: Boolean = false
)
