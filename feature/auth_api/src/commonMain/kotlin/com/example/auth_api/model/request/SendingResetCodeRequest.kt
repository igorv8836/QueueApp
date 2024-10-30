package com.example.auth_api.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SendingResetCodeRequest(
    val email: String
)