package com.example.network.util

import com.example.common.MyResult
import com.example.network.model.BaseResponse
import com.example.network.model.NetworkException
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.utils.io.errors.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): MyResult<T> {
    return try {
        MyResult.Success(apiCall())
    } catch (e: ClientRequestException) {
        val error = try {
            NetworkException.ClientErrorException(getErrorText(e))
        } catch (e: Exception) {
            NetworkException.UnexpectedException("${e.message}")
        }
        MyResult.Error(error)
    } catch (e: ServerResponseException) {
        val error = try {
            NetworkException.ServerErrorException(getErrorText(e))
        } catch (e: Exception) {
            NetworkException.UnexpectedException("${e.message}")
        }
        MyResult.Error(error)
    } catch (e: IOException) {
        MyResult.Error(NetworkException.NetworkIOException("${e.message}"))
    } catch (e: Exception) {
        MyResult.Error(NetworkException.UnexpectedException("${e.message}"))
    }
}


private suspend fun getErrorText(e: ResponseException): String{
    if (e.response.status.value == 401){
        return "Unauthorized"
    }
    return e.response.body<BaseResponse<String>>().message
}

