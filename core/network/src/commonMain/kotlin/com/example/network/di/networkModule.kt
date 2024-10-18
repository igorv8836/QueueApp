package com.example.network.di

import com.example.datastore.TokenManager
import com.example.network.getPlatformHttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.first
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.dsl.module

fun networkModule() = module {
    single(createdAtStart = true) {
        getReadyHttpClient(get())
    }
}

@OptIn(ExperimentalSerializationApi::class)
private fun getReadyHttpClient(tokenProvider: TokenManager) =
    getPlatformHttpClient().config {
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
            retryOnExceptionIf { _, cause -> cause is IOException }
        }
        install(UserAgent) {
            agent = "KMPApp/1.0"
        }
        install(WebSockets) {
            pingInterval = 15_000
            maxFrameSize = Long.MAX_VALUE
        }
        install(Auth) {
            bearer {
                loadTokens {
                    val token = tokenProvider.getToken().first()
                    if (!token.isNullOrEmpty()) {
                        BearerTokens(token, "")
                    } else {
                        null
                    }
                }
            }
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            url {
                protocol = URLProtocol.HTTPS
                host = "https://quickqueues.tech"
            }
        }

//        HttpResponseValidator {
//            handleResponseExceptionWithRequest { exception, _ ->
//                when (exception) {
//                    is ClientRequestException -> {
//                        // Ошибка 4xx
//                        val httpCode = exception.response.status.value
//                        when (httpCode){
//                            400 -> {  }
//                            401 -> {  }
//                            409 -> {  }
//                            else -> {  }
//                        }
//                    }
//                    is ServerResponseException -> {
//                        // Ошибка 5xx
//                        val response = exception.response
//                        println("Server error: ${response.status.value}")
//                    }
//                    is ResponseException -> {
//                        // Другая ошибка
//                        println("Unexpected error: ${exception.response.status.value}")
//                    }
//                    else -> {
//                        println("Unknown error: ${exception.message}")
//                    }
//                }
//            }
//        }
    }