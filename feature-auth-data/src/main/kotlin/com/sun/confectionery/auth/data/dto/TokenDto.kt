package com.sun.confectionery.auth.data.dto

data class TokenDto(
    val accessToken: String,
    val refreshToken: String
)