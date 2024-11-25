package com.example.database.model

import androidx.room.*

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val username: String,
    val photoUrl: String?
)