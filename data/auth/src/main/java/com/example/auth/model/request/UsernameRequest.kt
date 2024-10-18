package com.example.auth.model.request

import kotlinx.serialization.Serializable

@Serializable
data class UsernameRequest(
    val username: String
)