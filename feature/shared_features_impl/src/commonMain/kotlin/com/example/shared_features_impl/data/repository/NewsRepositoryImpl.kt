package com.example.shared_features_impl.data.repository

import com.example.common.MyResult
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
        when (val news = newsApiService.getNews()) {
            is MyResult.Error -> throw news.exception
            MyResult.Loading -> {}
            is MyResult.Success -> {
                newsDao.removeNews()
                newsDao.insertNews(news.data.map { it.toEntity() })
                emitAll(newsDao.getNews().map { it.map { it1 -> it1.toModel() } })
            }
        }
    }.flowOn(dispatcher)

    override suspend fun updateNews(): Result<Unit> {
        return when (val news = newsApiService.getNews()) {
            is MyResult.Error -> Result.failure(news.exception)
            MyResult.Loading -> Result.failure(Exception("Loading"))
            is MyResult.Success -> {
                newsDao.removeNews()
                newsDao.insertNews(news.data.map { it.toEntity() })
                Result.success(Unit)
            }
        }
    }
}