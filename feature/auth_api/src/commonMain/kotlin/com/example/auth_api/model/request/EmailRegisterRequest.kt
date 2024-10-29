package com.example.auth_api.model.request

import kotlinx.serialization.Serializable

@Serializable
data class EmailRegisterRequest(
    val email: String,
    val password: String,
    val username: String
)