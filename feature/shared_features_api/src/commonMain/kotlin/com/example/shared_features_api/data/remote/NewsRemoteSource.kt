package com.example.shared_features_api.data.remote

import com.example.shared_features_api.data.model.NewsModel

interface NewsRemoteSource {
    suspend fun getNews(): Result<List<NewsModel>>
}