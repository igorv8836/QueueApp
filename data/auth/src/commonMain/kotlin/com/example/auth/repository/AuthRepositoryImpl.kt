package com.example.auth.repository

import com.example.auth.model.request.EmailLoginRequest
import com.example.auth.model.request.EmailRegisterRequest
import com.example.auth.model.request.PasswordResetRequest
import com.example.auth.model.request.SendingResetCodeRequest
import com.example.auth.network.RemoteDataSource
import com.example.common.MyResult
import com.example.datastore.TokenManager
import com.example.network.model.NetworkException
import com.example.network.util.safeApiCall
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val api: RemoteDataSource,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun signUp(
        email: String,
        password: String,
        username: String
    ): MyResult<Unit> {
        return safeApiCall {
            val response = api.signUp(EmailRegisterRequest(email, password, username))
            if (response.success) {
                val token = response.message
                tokenManager.saveToken(token)
            } else {
                throw NetworkException.ClientErrorException(response.message)
            }
        }
    }

    override suspend fun login(email: String, password: String): MyResult<Unit> {
        return safeApiCall {
            val response = api.login(EmailLoginRequest(email, password))
            if (response.success) {
                val token = response.message
                tokenManager.saveToken(token)
            } else {
                throw NetworkException.ClientErrorException(response.message)
            }
        }
    }

    override suspend fun changePassword(email: String, newPassword: String): MyResult<String> {
        return safeApiCall {
            val response = api.changePassword(EmailLoginRequest(email, newPassword))
            if (response.success) {
                response.message
            } else {
                throw NetworkException.ClientErrorException(response.message)
            }
        }
    }

    override suspend fun resetPassword(email: String, resetCode: Int, newPassword: String): MyResult<String> {
        return safeApiCall {
            val response = api.resetPassword(
                PasswordResetRequest(email, resetCode, newPassword)
            )
            if (response.success) {
                response.message
            } else {
                throw NetworkException.ClientErrorException(response.message)
            }
        }
    }

    override suspend fun sendResetCode(email: String): MyResult<String> {
        return safeApiCall {
            val response = api.sendEmailResetCode(SendingResetCodeRequest(email))
            if (response.success) {
                response.message
            } else {
                throw NetworkException.ClientErrorException(response.message)
            }
        }
    }

    override fun getToken(): Flow<String?> {
        return tokenManager.getToken()
    }

    override suspend fun logout(): MyResult<Unit> {
        return try {
            tokenManager.removeToken()
            MyResult.Success(Unit)
        } catch (e: Exception) {
            MyResult.Error(NetworkException.UnexpectedException(e.message ?: ""))
        }
    }
}
