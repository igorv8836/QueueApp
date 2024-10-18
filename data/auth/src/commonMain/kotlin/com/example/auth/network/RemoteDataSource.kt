package com.example.auth.network

import com.example.auth.model.request.EmailLoginRequest
import com.example.auth.model.request.EmailRegisterRequest
import com.example.auth.model.request.PasswordResetRequest
import com.example.auth.model.request.SendingResetCodeRequest
import com.example.auth.model.request.UsernameRequest
import com.example.auth.model.response.EmailAccountResponse
import com.example.auth.model.response.UsernameCheckingResponse
import com.example.network.model.BaseResponse

interface RemoteDataSource {
    suspend fun changePassword(request: EmailLoginRequest): BaseResponse<String>
    suspend fun signUp(request: EmailRegisterRequest): BaseResponse<String>
    suspend fun getUserInfo(): EmailAccountResponse
    suspend fun isUsernameExisted(request: UsernameRequest): UsernameCheckingResponse
    suspend fun login(request: EmailLoginRequest): BaseResponse<String>
    suspend fun resetPassword(request: PasswordResetRequest): BaseResponse<String>
    suspend fun sendEmailResetCode(request: SendingResetCodeRequest): BaseResponse<String>
}