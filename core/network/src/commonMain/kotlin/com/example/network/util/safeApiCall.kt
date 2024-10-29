package com.example.network.util

import com.example.common.MyResult
import com.example.network.model.NetworkException
import io.ktor.client.call.body
import io.ktor.client.plugins.*
import io.ktor.client.statement.*
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
                MyResult.Error(NetworkException.ClientErrorException(response.bodyAsText()))
            }
            HttpStatusCode.Unauthorized -> {
                MyResult.Error(NetworkException.Unauthorized("UnAuthorized: " + response.bodyAsText()))
            }
            HttpStatusCode.Conflict -> {
                MyResult.Error(NetworkException.ClientErrorException("Conflict: " + response.bodyAsText()))
            }
            HttpStatusCode.TooManyRequests -> {
                MyResult.Error(NetworkException.ClientErrorException(response.bodyAsText()))
            }
            else -> {
                MyResult.Error(NetworkException.UnexpectedException("Unexpected status code: ${response.status.value}, ${response.bodyAsText()}"))
            }
        }
    } catch (e: ClientRequestException) {
        MyResult.Error(NetworkException.ClientErrorException(e.response.bodyAsText()))
    } catch (e: ServerResponseException) {
        MyResult.Error(NetworkException.ServerErrorException(e.response.bodyAsText()))
    } catch (e: kotlinx.io.IOException) {
        MyResult.Error(NetworkException.NetworkIOException("${e.message}"))
    } catch (e: Exception) {
        MyResult.Error(NetworkException.UnexpectedException("${e.message}"))
    }
}