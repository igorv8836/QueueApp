package com.example.network.util

import com.example.common.MyResult
import com.example.network.model.NetworkException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.utils.io.errors.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): MyResult<T> {
    return try {
        MyResult.Success(apiCall())
    } catch (e: ClientRequestException) {
        MyResult.Error(NetworkException.ClientErrorException(e.response.status.description))
    } catch (e: ServerResponseException) {
        MyResult.Error(NetworkException.ServerErrorException(e.response.status.description))
    } catch (e: IOException) {
        MyResult.Error(NetworkException.NetworkIOException("${e.message}"))
    } catch (e: Exception) {
        MyResult.Error(NetworkException.UnexpectedException("${e.message}"))
    }
}


