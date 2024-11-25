package com.example.queues_api.model.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateQueueRequest(
    val name: String,
    val description: String
)