package com.example.auth.model.response

import kotlinx.serialization.Serializable

@Serializable
data class UsernameCheckingResponse(
    val success: Boolean,
    val isExisted: Boolean
)