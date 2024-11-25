package com.example.queues_impl.data

import com.example.network.util.safeApiCall
import com.example.queues_api.data.QueueAdminRemoteSource
import com.example.queues_api.model.request.*
import com.example.queues_api.model.response.QueueModel
import io.ktor.client.HttpClient
import io.ktor.client.request.*

internal class QueueAdminApiService(
    private val client: HttpClient
) : QueueAdminRemoteSource {
    private val basePath = "/api/v1/queue/admin"
    override suspend fun createQueue(name: String, description: String): Result<Unit> {
        return safeApiCall {
            client.post("$basePath/login") {
                setBody(CreateQueueRequest(name, description))
            }
        }
    }

    override suspend fun deleteQueue(queueId: Int): Result<Unit> {
        return safeApiCall {
            client.delete("$basePath/$queueId")
        }
    }

    override suspend fun updateQueue(
        queueId: Int,
        name: String,
        description: String
    ): Result<Unit> {
        return safeApiCall {
            client.post("$basePath/update") {
                setBody(UpdateQueueRequest(queueId, name, description))
            }
        }
    }

    override suspend fun getQueue(queueId: Int): Result<QueueModel> {
        return safeApiCall {
            client.get("$basePath/$queueId")
        }
    }

    override suspend fun startQueue(queueId: Int): Result<Unit> {
        return safeApiCall {
            client.post("$basePath/$queueId/start")
        }
    }

    override suspend fun stopQueue(queueId: Int): Result<Unit> {
        return safeApiCall {
            client.post("$basePath/$queueId/stop")
        }
    }

    override suspend fun restartQueue(queueId: Int): Result<Unit> {
        return safeApiCall {
            client.post("$basePath/$queueId/restart")
        }
    }

    override suspend fun inviteUserByNickname(queueId: Int, username: String): Result<Unit> {
        return safeApiCall {
            client.post("$basePath/$queueId/invite/$username")
        }
    }

    override suspend fun generateLink(queueId: Int): Result<Unit> {
        return safeApiCall {
            client.post("$basePath/$queueId/link")
        }
    }

    override suspend fun deleteLink(queueId: Int): Result<Unit> {
        return safeApiCall {
            client.delete("$basePath/$queueId/link")
        }
    }

    override suspend fun randomizeQueue(queueId: Int): Result<Unit> {
        return safeApiCall {
            client.post("$basePath/$queueId/randomize")
        }
    }

    override suspend fun deleteUserFromQueue(queueId: Int, userId: Int): Result<Unit> {
        return safeApiCall {
            client.delete("$basePath/$queueId/user/$userId")
        }
    }

    override suspend fun moveUserInQueue(
        queueId: Int,
        userId: Int,
        newPosition: Int
    ): Result<Unit> {
        return safeApiCall {
            client.post("$basePath/$queueId/move/$userId/$newPosition")
        }
    }

    override suspend fun changeUserReadiness(
        queueId: Int,
        userId: Int,
        isReady: Boolean
    ): Result<Unit> {
        return safeApiCall {
            client.post("$basePath/$queueId/readiness/$userId/$isReady")
        }
    }
}