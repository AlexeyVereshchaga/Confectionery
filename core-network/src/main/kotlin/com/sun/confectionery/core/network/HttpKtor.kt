package com.sun.confectionery.core.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request

public suspend inline fun <reified T> HttpClient.startRequest(
    block: HttpClient.() -> HttpResponse
): T {
    val response = try {
        this.block()
    } catch (e: Exception) {
        throw e
    }

    if (!response.isSuccess()) {
        throw Exception("Response failed from ${response.request.url},\ncode:${response.status.value}\nbody: ${response.bodyAsText()}")
    }

    return response.body()
}

fun HttpResponse.isSuccess(): Boolean = status.value in 200..299