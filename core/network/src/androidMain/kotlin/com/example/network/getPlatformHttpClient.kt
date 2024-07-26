package com.example.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

internal actual fun getPlatformHttpClient() = HttpClient(OkHttp)