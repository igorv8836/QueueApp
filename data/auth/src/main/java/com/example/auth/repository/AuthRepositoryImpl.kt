package com.example.auth.repository

import com.example.auth.local.TokenManager
import com.example.auth.model.request.EmailLoginRequest
import com.example.auth.model.request.EmailRegisterRequest
import com.example.auth.network.AuthApiService
import com.example.common.MyResult
import com.example.network.model.NetworkException
import com.example.network.util.safeApiCall
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val dataStoreManager: TokenManager
) : AuthRepository {

    override suspend fun signUp(
        email: String,
        password: String,
        username: String
    ): MyResult<String> {
        return safeApiCall {
            val response = authApiService.signUp(EmailRegisterRequest(email, password, username))
            if (response.success) {
                val token = response.message
                dataStoreManager.saveToken(token)
                token
            } else {
                throw NetworkException.ClientErrorException(response.message)
            }
        }
    }

    override suspend fun login(email: String, password: String): MyResult<String> {
        return safeApiCall {
            val response = authApiService.login(EmailLoginRequest(email, password))
            if (response.success) {
                val token = response.message
                dataStoreManager.saveToken(token)
                token
            } else {
                throw NetworkException.ClientErrorException(response.message)
            }
        }
    }

    override fun getToken(): Flow<String?> {
        return dataStoreManager.getToken()
    }
}
