package com.example.network

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform