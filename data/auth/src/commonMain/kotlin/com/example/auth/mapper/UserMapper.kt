package com.example.auth.mapper

import com.example.auth.model.UserModel
import com.example.auth.model.response.EmailAccountResponse

internal fun UserModel.toEmailAccountResponse(): EmailAccountResponse {
    return EmailAccountResponse(
        email = this.email,
        username = this.username,
        photoUrl = this.photoUrl,
        notificationEnabled = this.notificationEnabled,
        isActive = this.isActive,
        banReason = this.banReason
    )
}

internal fun EmailAccountResponse.toUserModel(): UserModel {
    return UserModel(
        email = this.email,
        username = this.username,
        photoUrl = this.photoUrl,
        notificationEnabled = this.notificationEnabled,
        isActive = this.isActive,
        banReason = this.banReason
    )
}
