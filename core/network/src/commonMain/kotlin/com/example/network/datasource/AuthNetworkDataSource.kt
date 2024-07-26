package com.example.network.datasource

import domain.models.news.NewsModel

interface AuthNetworkDataSource {
    suspend fun test(): List<NewsModel>
}