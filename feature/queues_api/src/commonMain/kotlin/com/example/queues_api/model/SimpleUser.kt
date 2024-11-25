package com.example.queues_api.model

import kotlinx.serialization.Serializable

@Serializable
data class SimpleUser(
    val id: Int,
    val username: String,
    val photoUrl: String?
)