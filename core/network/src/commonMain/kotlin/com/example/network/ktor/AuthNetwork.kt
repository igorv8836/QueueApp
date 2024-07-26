package com.example.network.ktor

import com.example.network.datasource.AuthNetworkDataSource
import com.example.network.model.BaseResponse
import domain.models.news.NewsModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

internal class AuthNetwork(
    private val client: HttpClient
): AuthNetworkDataSource {
    override suspend fun test(): List<NewsModel> {
        return client.get("http://127.0.0.1:8080/api/v1/news").body<BaseResponse<List<NewsModel>>>().message
    }

}