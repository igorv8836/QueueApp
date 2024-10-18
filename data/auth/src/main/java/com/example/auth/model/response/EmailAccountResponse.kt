package domain.models.responses

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