package network

import com.example.auth.model.request.EmailLoginRequest
import com.example.auth.model.request.EmailRegisterRequest
import com.example.auth.model.request.PasswordChangeRequest
import com.example.auth.model.request.PasswordResetRequest
import com.example.auth.model.request.SendingResetCodeRequest
import com.example.auth.model.request.UsernameRequest
import com.example.auth.model.response.EmailAccountResponse
import com.example.auth.model.response.UsernameCheckingResponse
import com.example.auth.network.RemoteDataSource
import com.example.common.MyResult
import com.example.network.model.BaseResponse

class AuthApiServiceMock : RemoteDataSource {
    override suspend fun changePassword(request: PasswordChangeRequest): MyResult<BaseResponse<String>> {
        return MyResult.Success(BaseResponse(true, "success"))
    }

    override suspend fun signUp(request: EmailRegisterRequest): MyResult<BaseResponse<String>> {
        return MyResult.Success(BaseResponse(true, "success"))
    }

    override suspend fun getUserInfo(): MyResult<EmailAccountResponse> {
        return MyResult.Success(
            EmailAccountResponse(
                "email",
                "username",
                null,
                notificationEnabled = true,
                true,
                null
            )
        )
    }

    override suspend fun isUsernameExisted(request: UsernameRequest): MyResult<UsernameCheckingResponse> {
        return MyResult.Success(UsernameCheckingResponse(success = true, true))
    }

    override suspend fun login(request: EmailLoginRequest): MyResult<BaseResponse<String>> {
        return MyResult.Success(BaseResponse(true, "success"))
    }

    override suspend fun resetPassword(request: PasswordResetRequest): MyResult<BaseResponse<String>> {
        return MyResult.Success(BaseResponse(true, "success"))
    }

    override suspend fun sendEmailResetCode(request: SendingResetCodeRequest): MyResult<BaseResponse<String>> {
        return MyResult.Success(BaseResponse(true, "success"))
    }
}