package com.example.database.dao

import androidx.room.*
import com.example.database.model.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun getNews(): Flow<List<NewsEntity>>

    @Query("DELETE FROM news")
    suspend fun removeNews()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: List<NewsEntity>)
}