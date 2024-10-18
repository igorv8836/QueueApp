package domain.models.request

import kotlinx.serialization.Serializable

@Serializable
data class SendingResetCodeRequest(
    val email: String
)