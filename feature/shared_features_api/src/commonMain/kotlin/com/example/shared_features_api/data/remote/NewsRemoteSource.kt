package com.example.shared_features_api.data.remote

import com.example.common.MyResult
import com.example.shared_features_api.data.model.NewsModel

interface NewsRemoteSource {
    suspend fun getNews(): MyResult<List<NewsModel>>
}