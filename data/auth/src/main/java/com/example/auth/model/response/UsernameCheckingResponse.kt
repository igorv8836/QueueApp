package domain.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class UsernameCheckingResponse(
    val success: Boolean,
    val isExisted: Boolean
)