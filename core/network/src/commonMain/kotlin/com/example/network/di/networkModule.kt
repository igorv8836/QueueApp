package com.example.network.di

import com.example.network.datasource.AuthNetworkDataSource
import com.example.network.getPlatformHttpClient
import com.example.network.ktor.AuthNetwork
import io.ktor.client.plugins.DefaultRequest
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
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.dsl.bind
import org.koin.dsl.module

@OptIn(ExperimentalSerializationApi::class)
fun networkModule() = module {
    single { AuthNetwork(get()) } bind AuthNetworkDataSource::class
    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            explicitNulls = false
        }
    }

    single(createdAtStart = true) {
        getPlatformHttpClient().config {
            install(ContentNegotiation) {
                json(get())
            }
            install(Logging) {
                level = LogLevel.ALL
            }
            install(WebSockets) {
                pingInterval = 15
                maxFrameSize = Long.MAX_VALUE
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens("test", "test")
                    }
                }
            }
            install(DefaultRequest){
//                url {
//                    protocol = URLProtocol.HTTPS
//                    host = "http://127.0.0.1"
//                    port = 8080
//                }
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }
}