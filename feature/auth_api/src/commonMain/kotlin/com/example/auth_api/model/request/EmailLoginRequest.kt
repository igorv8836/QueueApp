package com.example.auth_api.model.request

import kotlinx.serialization.Serializable

@Serializable
data class EmailLoginRequest(
    val email: String,
    val password: String
)