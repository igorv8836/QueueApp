package com.example.auth.repository

import com.example.common.MyResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUp(email: String, password: String, username: String): MyResult<Unit>
    suspend fun login(email: String, password: String): MyResult<Unit>
    suspend fun changePassword(email: String, newPassword: String): MyResult<String>
    suspend fun resetPassword(email: String, resetCode: Int, newPassword: String): MyResult<String>
    suspend fun sendResetCode(email: String): MyResult<String>
    fun getToken(): Flow<String?>
    suspend fun logout(): MyResult<Unit>
}