package com.example.shared_features_api.data.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsModel(
    val id: Int,
    val title: String,
    val content: String,
    val createdAt: Long? = null
)
