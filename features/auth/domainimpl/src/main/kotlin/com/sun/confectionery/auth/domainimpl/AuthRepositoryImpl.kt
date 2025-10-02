package com.sun.confectionery.auth.domainimpl

import com.sun.confectionery.auth.data.AuthApi
import com.sun.confectionery.auth.data.dto.AuthDto
import com.sun.confectionery.auth.data.dto.LoginDto
import com.sun.confectionery.auth.domain.AuthRepository
import com.sun.confectionery.auth.domain.model.Token
import com.sun.confectionery.core.Outcome
import com.sun.confectionery.core.storage.TokenStorage

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val tokenStorage: TokenStorage
) : AuthRepository {
    override suspend fun register(email: String, password: String): Outcome<Token> {
        return try {
            val dto = AuthDto(email, password)
            val tokenDto = api.register(dto)
            tokenStorage.saveAccessToken(tokenDto.accessToken)
            tokenStorage.saveRefreshToken(tokenDto.refreshToken)
            Outcome.Success(Token(tokenDto.accessToken, tokenDto.refreshToken))
        } catch (ex: Throwable) {
            Outcome.Error(ex)
        }
    }

    override suspend fun login(email: String, password: String): Outcome<Token> {
        return try {
            val dto = LoginDto(email, password)
            val tokenDto = api.login(dto)
            tokenStorage.saveAccessToken(tokenDto.accessToken)
            tokenStorage.saveRefreshToken(tokenDto.refreshToken)
            Outcome.Success(Token(tokenDto.accessToken, tokenDto.refreshToken))
        } catch (ex: Throwable) {
            Outcome.Error(ex)
        }
    }

    override suspend fun refreshToken() {

    }

    override suspend fun logout() {

    }
}