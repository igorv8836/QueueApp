package com.example.database.model

import androidx.room.*

@Entity(tableName = "queue_admin")
data class QueueAdminEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val description: String?,
    val ownerId: Int,
    val isRunning: Boolean,
    val lastUpdated: Long,
    val link: String?
)