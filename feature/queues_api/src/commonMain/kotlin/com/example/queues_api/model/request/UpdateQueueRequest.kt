package com.example.queues_api.model.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateQueueRequest(
    val queueId: Int,
    val name: String,
    val description: String
)