package com.sun.confectionery.auth.data

import com.sun.confectionery.auth.data.dto.AuthDto
import com.sun.confectionery.auth.data.dto.LoginDto
import com.sun.confectionery.auth.data.dto.TokenDto
import com.sun.confectionery.core.network.AuthHttpClient
import com.sun.confectionery.core.network.startRequest
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthApiImpl(private val client: AuthHttpClient) : AuthApi {

    override suspend fun register(authDto: AuthDto): TokenDto {
        return client.client.startRequest {
            post("/register") {
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        }
    }

    override suspend fun login(loginDto: LoginDto): TokenDto {
        return client.client.startRequest {
            post("/admin/login") {
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        }
    }
}