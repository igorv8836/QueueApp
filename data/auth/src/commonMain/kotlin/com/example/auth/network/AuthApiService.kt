package com.example.auth.network

import com.example.auth.model.request.EmailLoginRequest
import com.example.auth.model.request.EmailRegisterRequest
import com.example.auth.model.request.PasswordChangeRequest
import com.example.auth.model.request.PasswordResetRequest
import com.example.auth.model.request.SendingResetCodeRequest
import com.example.auth.model.request.UsernameRequest
import com.example.auth.model.response.EmailAccountResponse
import com.example.auth.model.response.UsernameCheckingResponse
import com.example.common.MyResult
import com.example.network.model.BaseResponse
import com.example.network.util.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class AuthApiService(private val client: HttpClient) : RemoteDataSource {
    private val basePath = "/api/v1/auth"

    override suspend fun changePassword(request: PasswordChangeRequest): MyResult<BaseResponse<String>> {
        return safeApiCall {
            client.patch("$basePath/change_password") {
                setBody(request)
            }
        }
    }

    override suspend fun signUp(request: EmailRegisterRequest): MyResult<BaseResponse<String>> {
        return safeApiCall {
            client.post("$basePath/email_signup") {
                setBody(request)
            }
        }
    }

    override suspend fun getUserInfo(): MyResult<EmailAccountResponse> {
        return safeApiCall {
            client.get("$basePath/get-user-info").body()
        }
    }

    override suspend fun isUsernameExisted(request: UsernameRequest): MyResult<UsernameCheckingResponse> {
        return safeApiCall {
            client.get("$basePath/is_username_existed") {
                setBody(request)
            }
        }
    }

    override suspend fun login(request: EmailLoginRequest): MyResult<BaseResponse<String>> {
        return safeApiCall {
            client.post("$basePath/login") {
                setBody(request)
            }
        }
    }

    override suspend fun resetPassword(request: PasswordResetRequest): MyResult<BaseResponse<String>> {
        return safeApiCall {
            client.patch("$basePath/password_reset") {
                setBody(request)
            }
        }
    }

    override suspend fun sendEmailResetCode(request: SendingResetCodeRequest): MyResult<BaseResponse<String>> {
        return safeApiCall {
            client.post("$basePath/send_password_reset_code") {
                setBody(request)
            }
        }
    }
}