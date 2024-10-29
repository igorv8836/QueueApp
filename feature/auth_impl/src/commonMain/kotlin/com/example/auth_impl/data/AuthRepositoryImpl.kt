package com.example.auth_impl.data

import com.example.auth_api.data.*
import com.example.auth_api.model.UserModel
import com.example.auth_api.model.request.*
import com.example.auth_impl.domain.mapper.toUserModel
import com.example.common.MyResult
import com.example.datastore.TokenManager
import com.example.network.model.NetworkException
import kotlinx.coroutines.flow.Flow


@Suppress("UNCHECKED_CAST")
internal class AuthRepositoryImpl(
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
                tokenManager.saveToken(token)
                MyResult.Success(Unit)
            }

            else -> response as MyResult<Unit>
        }
    }

    override suspend fun login(email: String, password: String): MyResult<Unit> {
        return when (val response = api.login(EmailLoginRequest(email, password))) {
            is MyResult.Success -> {
                val token = response.data.message
                tokenManager.saveToken(token)
                MyResult.Success(Unit)
            }

            else -> response as MyResult<Unit>
        }
    }

    override suspend fun changePassword(
        email: String,
        oldPassword: String,
        newPassword: String
    ): MyResult<String> {
        return when (val response = api.changePassword(PasswordChangeRequest(email, oldPassword, newPassword))) {
            is MyResult.Success -> MyResult.Success(response.data.message)
            else -> response as MyResult<String>
        }
    }

    override suspend fun resetPassword(
        email: String,
        resetCode: Int,
        newPassword: String
    ): MyResult<String> {
        return when (val response = api.resetPassword(PasswordResetRequest(email, resetCode, newPassword))) {
            is MyResult.Success -> MyResult.Success(response.data.message)
            else -> response as MyResult<String>
        }
    }

    override suspend fun sendResetCode(email: String): MyResult<String> {
        return when (val response = api.sendEmailResetCode(SendingResetCodeRequest(email))) {
            is MyResult.Success -> MyResult.Success(response.data.message)
            else -> response as MyResult<String>
        }
    }

    override suspend fun getUser(): MyResult<UserModel> {
        return when (val response = api.getUserInfo()) {
            is MyResult.Success -> MyResult.Success(response.data.toUserModel())
            else -> response as MyResult<UserModel>

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