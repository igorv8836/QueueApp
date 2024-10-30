package com.example.shared_features_impl.mapper

import com.example.database.model.NewsEntity
import com.example.shared_features_api.data.model.NewsModel

internal fun NewsModel.toEntity() = NewsEntity(
    id = id,
    title = title,
    content = content,
    createdAt = createdAt
)

internal fun NewsEntity.toModel() = NewsModel(
    id = id,
    title = title,
    content = content,
    createdAt = createdAt
)