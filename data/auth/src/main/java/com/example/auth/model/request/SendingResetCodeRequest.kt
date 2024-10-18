package com.example.auth.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SendingResetCodeRequest(
    val email: String
)