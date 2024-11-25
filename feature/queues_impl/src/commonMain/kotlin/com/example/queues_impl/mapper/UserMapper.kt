package com.example.queues_impl.mapper

import com.example.database.model.UserEntity
import com.example.queues_api.model.SimpleUser

internal fun UserEntity.toSimpleUser(): SimpleUser {
    return SimpleUser(
        id = this.id,
        username = this.username,
        photoUrl = this.photoUrl
    )
}

internal fun SimpleUser.toUserEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        username = this.username,
        photoUrl = this.photoUrl
    )
}