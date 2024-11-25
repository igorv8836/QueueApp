package com.example.database.model

import androidx.room.*

@Entity(
    tableName = "queue_member",
    primaryKeys = ["queueId", "userId"],
    foreignKeys = [
        ForeignKey(
            entity = QueueAdminEntity::class,
            parentColumns = ["id"],
            childColumns = ["queueId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class QueueMemberEntity(
    val queueId: Int,
    val userId: Int,
    val position: Int,
    val isReady: Boolean
)