package com.sun.confectionery.auth.data

import com.sun.confectionery.auth.data.dto.AuthDto
import com.sun.confectionery.auth.data.dto.LoginDto
import com.sun.confectionery.auth.data.dto.TokenDto

interface AuthApi {
    suspend fun register(authDto: AuthDto): TokenDto
    suspend fun login(loginDto: LoginDto): TokenDto
}