package domain.models

import kotlinx.serialization.Serializable
import models.User
import utils.toTimestampMoscow
import java.time.LocalDateTime

@Serializable
data class UserModel(
    override val id: Int,
    val email: String,
    val googleAccount: String?,
    val passwordHash: String?,
    override val username: String,
    override val photoUrl: String?,
    val notificationEnabled: Boolean,
    val isActive: Boolean,
    val banReason: String?,
    val createdAt: Long,
    override val isAdmin: Boolean = false
) : User {

    constructor(
        email: String,
        passwordHash: String,
        username: String,
    ) : this(0, email, null, passwordHash, username, null, true, true, null, LocalDateTime.now().toTimestampMoscow())

    constructor(
        googleAccount: String,
        email: String,
        username: String,
        photoUrl: String,
    ) : this(
        0,
        email,
        googleAccount,
        null,
        username,
        photoUrl,
        true,
        true,
        null,
        LocalDateTime.now().toTimestampMoscow()
    )
}
