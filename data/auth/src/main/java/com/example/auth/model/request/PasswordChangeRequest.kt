package com.example.auth.model.request

import kotlinx.serialization.Serializable

@Serializable
data class PasswordChangeRequest(
    val email: String,
    val oldPassword: String,
    val newPassword: String
)