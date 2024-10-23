package com.example.network.di

import com.example.datastore.TokenManager
import com.example.network.getPlatformHttpClient
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.header
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import kotlin.time.Duration.Companion.seconds

fun networkModule() = module {
    single(createdAtStart = true) {
        getReadyHttpClient(get())
    }
}

private fun getReadyHttpClient(tokenProvider: TokenManager): HttpClient {
    Napier.i(message = "start", tag = "startClient")
    return getPlatformHttpClient().config {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                explicitNulls = false
            })
        }
        install(Logging) {
            level = LogLevel.ALL
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 45_000
            connectTimeoutMillis = 20_000
            socketTimeoutMillis = 45_000
        }
        install(HttpRequestRetry) {
            maxRetries = 3
            retryOnExceptionIf { _, cause -> cause is kotlinx.io.IOException }
        }
        install(UserAgent) {
            agent = "KMPApp/1.0"
        }
        install(WebSockets) {
            pingInterval = 15.seconds
            maxFrameSize = Long.MAX_VALUE
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            url {
                protocol = URLProtocol.HTTPS
                host = "quickqueues.tech"
            }

            val token = runBlocking {
                tokenProvider.getToken().first()
            }
            header(HttpHeaders.Authorization, "Bearer $token")
        }
    }
}