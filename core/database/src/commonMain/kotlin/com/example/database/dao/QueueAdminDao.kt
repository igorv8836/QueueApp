package com.example.database.dao

import androidx.room.*
import com.example.database.model.QueueAdminEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QueueAdminDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQueue(queue: QueueAdminEntity)

    @Query("DELETE FROM queue_admin WHERE id = :id")
    suspend fun deleteQueue(id: Int)

    @Update
    suspend fun updateQueue(queue: QueueAdminEntity)

    @Query("SELECT * FROM queue_admin WHERE id = :id")
    fun getQueue(id: Int): Flow<QueueAdminEntity>

    @Query("SELECT * FROM queue_admin")
    fun getAllQueues(): Flow<List<QueueAdminEntity>>

}