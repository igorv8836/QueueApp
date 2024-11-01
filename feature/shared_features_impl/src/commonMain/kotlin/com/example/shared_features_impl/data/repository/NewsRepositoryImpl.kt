package com.example.shared_features_impl.data.repository

import com.example.database.dao.NewsDao
import com.example.shared_features_api.data.NewsRepository
import com.example.shared_features_api.data.remote.NewsRemoteSource
import com.example.shared_features_impl.mapper.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

internal class NewsRepositoryImpl(
    private val newsApiService: NewsRemoteSource,
    private val newsDao: NewsDao,
    private val dispatcher: CoroutineDispatcher

) : NewsRepository {
    override fun getNews() = flow {
        val res = newsApiService.getNews()
        val news = res.getOrThrow()
        newsDao.removeNews()
        newsDao.insertNews(news.map { it.toEntity() })
        emitAll(newsDao.getNews().map { it.map { it1 -> it1.toModel() } })
    }.flowOn(dispatcher)

    override suspend fun updateNews(): Result<Unit> {
        val res = newsApiService.getNews()
        val news = res.getOrElse { return Result.failure(it) }
        newsDao.removeNews()
        newsDao.insertNews(news.map { it.toEntity() })
        return Result.success(Unit)
    }
}