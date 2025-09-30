package com.sun.confectionery.auth.domain

import com.sun.confectionery.auth.domain.model.Token
import com.sun.confectionery.core.Outcome

interface AuthRepository {
    suspend fun register(email: String, password: String): Outcome<Token>
    suspend fun login(email: String, password: String):Outcome<Token>
    suspend fun refreshToken()
    suspend fun logout()
}