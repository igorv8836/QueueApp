package com.example.queues_api.model.response

import kotlinx.serialization.Serializable

@Serializable
data class QueueModel(
    val id: Int,
    val name: String,
    val description: String?,
    val ownerId: Int,
    val isRunning: Boolean,
    val lastUpdated: Long,
    val link: String?,
    val members: List<QueueParticipantModel>
)
