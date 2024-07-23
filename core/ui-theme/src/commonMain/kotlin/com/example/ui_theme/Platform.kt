package com.example.ui_theme

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform