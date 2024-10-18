package com.example.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val email: String,
    val username: String,
    val photoUrl: String?,
    val notificationEnabled: Boolean,
    val isActive: Boolean,
    val banReason: String?
)