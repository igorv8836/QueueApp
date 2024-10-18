package com.example.auth.network

import com.example.auth.model.request.EmailLoginRequest
import com.example.auth.model.request.EmailRegisterRequest
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
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.request
import io.ktor.util.reflect.TypeInfo

class AuthApiService(private val client: HttpClient) : RemoteDataSource {
    private val basePath = "/api/v1/auth"

    override suspend fun changePassword(request: EmailLoginRequest): BaseResponse<String> {
        return client.patch("$basePath/change_password") {
            setBody(request)
        }.body()
    }

    override suspend fun signUp(request: EmailRegisterRequest): BaseResponse<String> {
        return client.post("$basePath/email_signup") {
            setBody(request)
        }.body()
    }

    override suspend fun getUserInfo(): EmailAccountResponse {
        return client.get("$basePath/get-user-info").body()
    }

    override suspend fun isUsernameExisted(request: UsernameRequest): UsernameCheckingResponse {
        return client.get("$basePath/is_username_existed") {
            setBody(request)
        }.body()
    }

    override suspend fun login(request: EmailLoginRequest): BaseResponse<String> {
        return client.post("$basePath/login") {
            setBody(request)
        }.body()
    }

    override suspend fun resetPassword(request: PasswordResetRequest): BaseResponse<String> {
        return client.patch("$basePath/password_reset") {
            setBody(request)
        }.body()
    }

    override suspend fun sendEmailResetCode(request: SendingResetCodeRequest): BaseResponse<String> {
        return client.post("$basePath/send_password_reset_code") {
            setBody(request)
        }.body()
    }
}