package domain.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoogleAccountResponse(
    val sub: String,
    val name: String,
    @SerialName("given_name") val givenName: String,
    val picture: String,
    val email: String,
    @SerialName("email_verified") val emailVerified: Boolean
)