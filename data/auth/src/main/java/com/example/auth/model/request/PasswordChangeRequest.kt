package domain.models.request

import kotlinx.serialization.Serializable

@Serializable
data class PasswordChangeRequest(
    val email: String,
    val oldPassword: String,
    val newPassword: String
)