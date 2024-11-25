package com.example.queues_impl.mapper

import com.example.database.model.QueueAdminEntity
import com.example.queues_api.model.response.*

internal fun QueueAdminEntity.toQueueModel(members: List<QueueParticipantModel>): QueueModel {
    return QueueModel(
        id = this.id,
        name = this.name,
        description = this.description,
        ownerId = this.ownerId,
        isRunning = this.isRunning,
        lastUpdated = this.lastUpdated,
        link = this.link,
        members = members
    )
}

internal fun QueueModel.toQueueAdminEntity(): QueueAdminEntity {
    return QueueAdminEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        ownerId = this.ownerId,
        isRunning = this.isRunning,
        lastUpdated = this.lastUpdated,
        link = this.link
    )
}