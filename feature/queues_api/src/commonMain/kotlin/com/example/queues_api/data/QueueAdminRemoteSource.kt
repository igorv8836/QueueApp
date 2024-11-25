package com.example.queues_api.data

import com.example.queues_api.model.response.QueueModel

interface QueueAdminRemoteSource {
    suspend fun createQueue(name: String, description: String): Result<Unit>
    suspend fun deleteQueue(queueId: Int): Result<Unit>
    suspend fun updateQueue(queueId: Int, name: String, description: String): Result<Unit>
    suspend fun getQueue(queueId: Int): Result<QueueModel>
    suspend fun startQueue(queueId: Int): Result<Unit>
    suspend fun stopQueue(queueId: Int): Result<Unit>
    suspend fun restartQueue(queueId: Int): Result<Unit>
    suspend fun inviteUserByNickname(queueId: Int, username: String): Result<Unit>
    suspend fun generateLink(queueId: Int): Result<Unit>
    suspend fun deleteLink(queueId: Int): Result<Unit>
    suspend fun randomizeQueue(queueId: Int): Result<Unit>


    suspend fun deleteUserFromQueue(queueId: Int, userId: Int): Result<Unit>
    suspend fun moveUserInQueue(queueId: Int, userId: Int, newPosition: Int): Result<Unit>
    suspend fun changeUserReadiness(queueId: Int, userId: Int, isReady: Boolean): Result<Unit>
}