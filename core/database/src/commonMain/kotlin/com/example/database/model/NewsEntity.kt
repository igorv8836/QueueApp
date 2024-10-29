package com.example.database.model

import androidx.room.*

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val content: String,
    val createdAt: Long?
)