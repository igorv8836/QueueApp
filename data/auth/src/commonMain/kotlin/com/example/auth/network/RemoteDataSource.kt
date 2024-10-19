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

interface RemoteDataSource {
    suspend fun changePassword(request: PasswordChangeRequest): MyResult<BaseResponse<String>>
    suspend fun signUp(request: EmailRegisterRequest): MyResult<BaseResponse<String>>
    suspend fun getUserInfo(): MyResult<EmailAccountResponse>
    suspend fun isUsernameExisted(request: UsernameRequest): MyResult<UsernameCheckingResponse>
    suspend fun login(request: EmailLoginRequest): MyResult<BaseResponse<String>>
    suspend fun resetPassword(request: PasswordResetRequest): MyResult<BaseResponse<String>>
    suspend fun sendEmailResetCode(request: SendingResetCodeRequest): MyResult<BaseResponse<String>>
}
