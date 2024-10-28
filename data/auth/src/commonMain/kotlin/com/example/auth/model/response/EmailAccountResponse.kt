package com.example.auth.model.response

import kotlinx.serialization.Serializable

@Serializable
data class EmailAccountResponse(
    val email: String,
    val username: String,
    val photoUrl: String?,
    val notificationEnabled: Boolean,
    val isActive: Boolean,
    val banReason: String?
)