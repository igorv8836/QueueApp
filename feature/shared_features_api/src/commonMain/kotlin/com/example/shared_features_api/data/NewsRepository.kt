package com.example.shared_features_api.data

import com.example.shared_features_api.data.model.NewsModel
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(): Flow<List<NewsModel>>
}