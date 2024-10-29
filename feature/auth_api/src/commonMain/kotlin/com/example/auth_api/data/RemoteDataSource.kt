package com.example.auth_api.data

import com.example.auth_api.model.request.*
import com.example.auth_api.model.response.*
import com.example.common.MyResult
import com.example.network.model.BaseResponse

interface RemoteDataSource {
    suspend fun changePassword(request: PasswordChangeRequest): MyResult<BaseResponse<String>>
    suspend fun signUp(request: EmailRegisterRequest): MyResult<BaseResponse<String>>
    suspend fun getUserInfo(): MyResult<EmailAccountResponse>
    suspend fun isUsernameExisted(request: UsernameRequest): MyResult<UsernameCheckingResponse>
    suspend fun login(request: EmailLoginRequest): MyResult<BaseResponse<String>>
    suspend fun resetPassword(request: PasswordResetRequest): MyResult<BaseResponse<String>>
    suspend fun sendEmailResetCode(request: SendingResetCodeRequest): MyResult<BaseResponse<String>>
}
