package com.example.database

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform