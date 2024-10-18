package domain.models.request

import kotlinx.serialization.Serializable

@Serializable
data class UsernameRequest(
    val username: String
)