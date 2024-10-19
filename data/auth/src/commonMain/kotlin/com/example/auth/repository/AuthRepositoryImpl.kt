package com.example.auth.repository

import com.example.auth.model.request.EmailLoginRequest
import com.example.auth.model.request.EmailRegisterRequest
import com.example.auth.model.request.PasswordChangeRequest
import com.example.auth.model.request.PasswordResetRequest
import com.example.auth.model.request.SendingResetCodeRequest
import com.example.auth.network.RemoteDataSource
import com.example.common.MyResult
import com.example.datastore.TokenManager
import com.example.network.model.NetworkException
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
        return when (val response = api.signUp(EmailRegisterRequest(email, password, username))) {
            is MyResult.Success -> {
                val token = response.data.message
                if (response.success) {
                    tokenManager.saveToken(token)
                    MyResult.Success(Unit)
                } else {
                    MyResult.Error(NetworkException.UnexpectedException(response.data.message))
                }
            }

            is MyResult.Loading -> response
            is MyResult.Error -> response
        }
    }

    override suspend fun login(email: String, password: String): MyResult<Unit> {
        return when (val response = api.login(EmailLoginRequest(email, password))) {
            is MyResult.Success -> {
                val token = response.data.message
                if (response.data.success) {
                    tokenManager.saveToken(token)
                    MyResult.Success(Unit)
                } else {
                    MyResult.Error(NetworkException.UnexpectedException(response.data.message))
                }
            }
            is MyResult.Loading -> response
            is MyResult.Error -> response
        }
    }

    override suspend fun changePassword(
        email: String,
        oldPassword: String,
        newPassword: String
    ): MyResult<String> {
        return when (val response = api.changePassword(PasswordChangeRequest(email, oldPassword, newPassword))) {
            is MyResult.Success -> {
                if (response.data.success) {
                    MyResult.Success(response.data.message)
                } else {
                    MyResult.Error(NetworkException.UnexpectedException(response.data.message))
                }
            }
            is MyResult.Loading -> response
            is MyResult.Error -> response
        }
    }

    override suspend fun resetPassword(
        email: String,
        resetCode: Int,
        newPassword: String
    ): MyResult<String> {
        return when (val response = api.resetPassword(PasswordResetRequest(email, resetCode, newPassword))) {
            is MyResult.Success -> {
                if (response.data.success) {
                    MyResult.Success(response.data.message)
                } else {
                    MyResult.Error(NetworkException.UnexpectedException(response.data.message))
                }
            }
            is MyResult.Loading -> response
            is MyResult.Error -> response
        }
    }

    override suspend fun sendResetCode(email: String): MyResult<String> {
        return when (val response = api.sendEmailResetCode(SendingResetCodeRequest(email))) {
            is MyResult.Success -> {
                if (response.data.success) {
                    MyResult.Success(response.data.message)
                } else {
                    MyResult.Error(NetworkException.UnexpectedException(response.data.message))
                }
            }
            is MyResult.Loading -> response
            is MyResult.Error -> response
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