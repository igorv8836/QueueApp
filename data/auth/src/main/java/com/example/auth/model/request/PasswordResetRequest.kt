package domain.models.request

import kotlinx.serialization.Serializable

@Serializable
data class PasswordResetRequest(
    val email: String,
    val code: Int,
    val newPassword: String
)