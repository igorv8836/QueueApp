package com.example.auth.model.request

import kotlinx.serialization.Serializable

@Serializable
data class PasswordResetRequest(
    val email: String,
    val code: Int,
    val newPassword: String
)