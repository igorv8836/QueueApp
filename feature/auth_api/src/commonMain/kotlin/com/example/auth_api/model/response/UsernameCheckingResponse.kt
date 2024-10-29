package com.example.auth_api.model.response

import kotlinx.serialization.Serializable

@Serializable
data class UsernameCheckingResponse(
    val success: Boolean,
    val isExisted: Boolean
)