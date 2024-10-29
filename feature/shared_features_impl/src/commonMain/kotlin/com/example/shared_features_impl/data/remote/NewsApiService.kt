package com.example.shared_features_impl.data.remote

import com.example.common.MyResult
import com.example.network.util.safeApiCall
import com.example.shared_features_api.data.model.NewsModel
import com.example.shared_features_api.data.remote.NewsRemoteSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

internal class NewsApiService(
    private val httpClient: HttpClient
) : NewsRemoteSource {
    private val basePath = "/api/v1/news"

    override suspend fun getNews(): MyResult<List<NewsModel>> {
        return safeApiCall {
            httpClient.get(basePath)
        }
    }
}