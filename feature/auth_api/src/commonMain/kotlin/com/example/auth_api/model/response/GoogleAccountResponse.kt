package com.example.auth_api.model.response

import kotlinx.serialization.*

@Serializable
data class GoogleAccountResponse(
    val sub: String,
    val name: String,
    @SerialName("given_name") val givenName: String,
    val picture: String,
    val email: String,
    @SerialName("email_verified") val emailVerified: Boolean
)