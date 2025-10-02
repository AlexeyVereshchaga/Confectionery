package com.sun.confectionery.auth.domain.model

data class Token(
    val accessToken: String,
    val refreshToken: String
)
