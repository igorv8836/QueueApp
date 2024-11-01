package com.example.auth_impl.data

import com.example.auth_api.data.*
import com.example.auth_api.model.UserModel
import com.example.auth_api.model.request.*
import com.example.auth_impl.domain.mapper.toUserModel
import com.example.datastore.TokenManager
import com.example.network.model.NetworkException


internal class AuthRepositoryImpl(
    private val api: RemoteDataSource,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun signUp(
        email: String,
        password: String,
        username: String
    ): Result<Unit> {
        return api.signUp(EmailRegisterRequest(email, password, username))
            .mapCatching { response ->
                val token = response.message
                tokenManager.saveToken(token)
            }
    }

    override suspend fun login(email: String, password: String): Result<Unit> {
        return api.login(EmailLoginRequest(email, password))
            .mapCatching { response ->
                val token = response.message
                tokenManager.saveToken(token)
            }
    }

    override suspend fun changePassword(
        email: String,
        oldPassword: String,
        newPassword: String
    ): Result<String> {
        return api.changePassword(PasswordChangeRequest(email, oldPassword, newPassword))
            .map { response -> response.message }
    }

    override suspend fun resetPassword(
        email: String,
        resetCode: Int,
        newPassword: String
    ): Result<String> {
        return api.resetPassword(PasswordResetRequest(email, resetCode, newPassword))
            .map { response -> response.message }
    }

    override suspend fun sendResetCode(email: String): Result<String> {
        return api.sendEmailResetCode(SendingResetCodeRequest(email))
            .map { response -> response.message }
    }

    override suspend fun getUser(): Result<UserModel> {
        return api.getUserInfo()
            .map { response -> response.toUserModel() }
    }

    override fun getToken() = tokenManager.getToken()

    override suspend fun logout(): Result<Unit> {
        return try {
            tokenManager.removeToken()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(NetworkException.UnexpectedException(e.message ?: ""))
        }
    }
}