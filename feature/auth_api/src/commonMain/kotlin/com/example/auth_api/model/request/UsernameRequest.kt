package com.example.auth_api.model.request

import kotlinx.serialization.Serializable

@Serializable
data class UsernameRequest(
    val username: String
)