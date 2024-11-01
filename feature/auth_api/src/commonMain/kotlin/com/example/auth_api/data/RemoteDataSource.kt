package com.example.auth_api.data

import com.example.auth_api.model.request.EmailLoginRequest
import com.example.auth_api.model.request.EmailRegisterRequest
import com.example.auth_api.model.request.PasswordChangeRequest
import com.example.auth_api.model.request.PasswordResetRequest
import com.example.auth_api.model.request.SendingResetCodeRequest
import com.example.auth_api.model.request.UsernameRequest
import com.example.auth_api.model.response.EmailAccountResponse
import com.example.auth_api.model.response.UsernameCheckingResponse
import com.example.network.model.BaseResponse

interface RemoteDataSource {
    suspend fun changePassword(request: PasswordChangeRequest): Result<BaseResponse<String>>
    suspend fun signUp(request: EmailRegisterRequest): Result<BaseResponse<String>>
    suspend fun getUserInfo(): Result<EmailAccountResponse>
    suspend fun isUsernameExisted(request: UsernameRequest): Result<UsernameCheckingResponse>
    suspend fun login(request: EmailLoginRequest): Result<BaseResponse<String>>
    suspend fun resetPassword(request: PasswordResetRequest): Result<BaseResponse<String>>
    suspend fun sendEmailResetCode(request: SendingResetCodeRequest): Result<BaseResponse<String>>
}
