package com.example.auth.repository

import com.example.common.MyResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUp(email: String, password: String, username: String): MyResult<String>
    suspend fun login(email: String, password: String): MyResult<String>
    fun getToken(): Flow<String?>
}