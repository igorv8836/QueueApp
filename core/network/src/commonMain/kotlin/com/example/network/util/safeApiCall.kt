package com.example.network.util

import com.example.common.MyResult
import com.example.network.model.BaseResponse
import com.example.network.model.NetworkException
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode

suspend inline fun <reified T> safeApiCall(apiCall: () -> HttpResponse): MyResult<T> {
    return try {
        val response = apiCall()
        when (response.status) {
            HttpStatusCode.OK -> {
                val responseBody = response.body<T>()
                MyResult.Success(responseBody)
            }
            HttpStatusCode.BadRequest -> {
                MyResult.Error(NetworkException.ClientErrorException("BadRequest: " + response.body<BaseResponse<String>>().message))
            }
            HttpStatusCode.Unauthorized -> {
                MyResult.Error(NetworkException.ClientErrorException("UnAuthorized: " + response.bodyAsText()))
            }
            HttpStatusCode.Conflict -> {
                MyResult.Error(NetworkException.ClientErrorException("Conflict: " + response.body<BaseResponse<String>>().message))
            }
            else -> {
                MyResult.Error(NetworkException.UnexpectedException("Unexpected status code: ${response.status.value}, ${response.bodyAsText()}"))
            }
        }
    } catch (e: ClientRequestException) {
        MyResult.Error(NetworkException.ClientErrorException(getErrorText(e)))
    } catch (e: ServerResponseException) {
        MyResult.Error(NetworkException.ServerErrorException(getErrorText(e)))
    } catch (e: kotlinx.io.IOException) {
        MyResult.Error(NetworkException.NetworkIOException("${e.message}"))
    } catch (e: Exception) {
        MyResult.Error(NetworkException.UnexpectedException("${e.message}"))
    }
}



suspend fun getErrorText(e: ResponseException): String{
    return try {
        e.response.body<BaseResponse<String>>().message
    } catch (e: Exception) {
        e.message ?: "Error"
    }
}

