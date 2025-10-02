package com.sun.confectionery.core.network

import com.sun.confectionery.core.ApiConfig
import com.sun.confectionery.core.auth.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class AuthHttpClient(private val tokenStorage: TokenStorage) {
    val client = createClient()

    fun createClient(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                    isLenient = true
                })
            }

            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.ALL
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        val accessToken = tokenStorage.getAccessToken()
                        val refreshToken = tokenStorage.getRefreshToken()
                        if (accessToken != null && refreshToken != null) {
                            BearerTokens(accessToken, refreshToken)
                        } else {
                            null
                        }
                    }

                    refreshTokens {
                        val currentRefreshToken = tokenStorage.getRefreshToken()
                        if (currentRefreshToken == null) {
                            tokenStorage.clear()
                            null
                        } else {
                            val newTokens = performTokenRefresh(currentRefreshToken)
                            if (newTokens != null) {
                                tokenStorage.saveAccessToken(newTokens.first)
                                tokenStorage.saveRefreshToken(newTokens.second)
                                BearerTokens(newTokens.first, newTokens.second)
                            } else {
                                tokenStorage.clear()
                                null
                            }
                        }
                    }
                }
            }

            defaultRequest {
                url {
                    protocol = URLProtocol.HTTP
                    host = ApiConfig.BASE_URL
                    port = ApiConfig.PORT
                }
            }
        }
    }

    private suspend fun performTokenRefresh(refreshToken: String): Pair<String, String>? {
        // TODO: Implement actual token refresh logic here
        return null
    }
}
