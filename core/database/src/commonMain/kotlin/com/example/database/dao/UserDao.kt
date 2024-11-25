package com.example.database.dao

import androidx.room.*
import com.example.database.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query("DELETE FROM user WHERE id = :id")
    suspend fun deleteUser(id: Int)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUser(id: Int): UserEntity?

    @Query("SELECT * FROM user")
    fun getAllUsers(): Flow<List<UserEntity>>
}