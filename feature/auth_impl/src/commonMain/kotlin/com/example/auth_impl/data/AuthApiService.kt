package com.example.auth_impl.data

import com.example.auth_api.data.RemoteDataSource
import com.example.auth_api.model.request.*
import com.example.auth_api.model.response.*
import com.example.network.model.BaseResponse
import com.example.network.util.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.*

internal class AuthApiService(private val client: HttpClient) : RemoteDataSource {
    private val basePath = "/api/v1/auth"

    override suspend fun changePassword(request: PasswordChangeRequest): Result<BaseResponse<String>> {
        return safeApiCall {
            client.patch("$basePath/change_password") {
                setBody(request)
            }
        }
    }

    override suspend fun signUp(request: EmailRegisterRequest): Result<BaseResponse<String>> {
        return safeApiCall {
            client.post("$basePath/email_signup") {
                setBody(request)
            }
        }
    }

    override suspend fun getUserInfo(): Result<EmailAccountResponse> {
        return safeApiCall {
            client.get("$basePath/get-user-info").body()
        }
    }

    override suspend fun isUsernameExisted(request: UsernameRequest): Result<UsernameCheckingResponse> {
        return safeApiCall {
            client.get("$basePath/is_username_existed") {
                setBody(request)
            }
        }
    }

    override suspend fun login(request: EmailLoginRequest): Result<BaseResponse<String>> {
        return safeApiCall {
            client.post("$basePath/login") {
                setBody(request)
            }
        }
    }

    override suspend fun resetPassword(request: PasswordResetRequest): Result<BaseResponse<String>> {
        return safeApiCall {
            client.patch("$basePath/password_reset") {
                setBody(request)
            }
        }
    }

    override suspend fun sendEmailResetCode(request: SendingResetCodeRequest): Result<BaseResponse<String>> {
        return safeApiCall {
            client.post("$basePath/send_password_reset_code") {
                setBody(request)
            }
        }
    }
}