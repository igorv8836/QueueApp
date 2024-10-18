package com.example.auth.model.response

import com.example.auth.model.UserModel
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

internal fun EmailAccountResponse.toUserModel(): UserModel {
    return UserModel(
        email = email,
        username = username,
        photoUrl = photoUrl,
        notificationEnabled = notificationEnabled,
        isActive = isActive,
        banReason = banReason
    )
}