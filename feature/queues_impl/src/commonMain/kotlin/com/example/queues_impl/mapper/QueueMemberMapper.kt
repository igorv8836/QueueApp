package com.example.queues_impl.mapper

import com.example.database.model.QueueMemberEntity
import com.example.queues_api.model.SimpleUser
import com.example.queues_api.model.response.QueueParticipantModel

internal fun QueueMemberEntity.toQueueParticipantModel(user: SimpleUser): QueueParticipantModel {
    return QueueParticipantModel(
        id = this.userId,
        user = user,
        position = this.position,
        isReady = this.isReady
    )
}

internal fun QueueParticipantModel.toQueueMemberEntity(queueId: Int): QueueMemberEntity {
    return QueueMemberEntity(
        queueId = queueId,
        userId = this.user.id,
        position = this.position,
        isReady = this.isReady
    )
}