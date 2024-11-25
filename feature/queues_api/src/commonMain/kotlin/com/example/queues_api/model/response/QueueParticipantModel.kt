package com.example.queues_api.model.response

import com.example.queues_api.model.SimpleUser
import kotlinx.serialization.Serializable

@Serializable
data class QueueParticipantModel(
    val id: Int,
    val user: SimpleUser,
    val position: Int,
    val isReady: Boolean
)