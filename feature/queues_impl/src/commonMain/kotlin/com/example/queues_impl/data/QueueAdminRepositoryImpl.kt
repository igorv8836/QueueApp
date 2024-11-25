package com.example.queues_impl.data

import com.example.database.dao.*
import com.example.queues_api.data.QueueAdminRepository
import com.example.queues_api.model.SimpleUser
import com.example.queues_api.model.response.QueueModel
import com.example.queues_impl.mapper.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

internal class QueueAdminRepositoryImpl(
    private val apiService: QueueAdminApiService,
    private val queueAdminDao: QueueAdminDao,
    private val queueMemberDao: QueueMemberDao,
    private val userDao: UserDao,
    private val ioDispatcher: CoroutineDispatcher
) : QueueAdminRepository {
    override suspend fun createQueue(name: String, description: String): Result<Unit> {
        return Result.runCatching {
            apiService.createQueue(name, description).getOrThrow()
        }
    }

    override suspend fun deleteQueue(queueId: Int): Result<Unit> {
        return Result.runCatching {
            apiService.deleteQueue(queueId).getOrThrow()
            queueAdminDao.deleteQueue(queueId)
        }
    }

    override suspend fun updateQueue(
        queueId: Int,
        name: String,
        description: String
    ): Result<Unit> {
        return runCatching {
            apiService.updateQueue(queueId, name, description).getOrThrow()
            val updatedQueue = queueAdminDao.getQueue(queueId).first().copy(
                name = name,
                description = description
            )
            queueAdminDao.updateQueue(updatedQueue)
        }
    }

    override fun getQueue(queueId: Int): Flow<QueueModel> {
        return flow {
            val localDataFlow = queueAdminDao.getQueue(queueId).map { queue ->
                val members = queueMemberDao.getMembersByQueueId(queue.id).map { member ->
                    member.toQueueParticipantModel(
                        userDao.getUser(member.userId)?.toSimpleUser() ?: SimpleUser(
                            member.userId,
                            "Unknown",
                            null
                        )
                    )
                }
                queue.toQueueModel(members)
            }
            emit(localDataFlow.first())
            val newData = apiService.getQueue(queueId).getOrThrow()
            queueAdminDao.insertQueue(newData.toQueueAdminEntity())
            queueMemberDao.deleteMembersByQueueId(queueId)
            newData.members.forEach { member ->
                queueMemberDao.insertMember(member.toQueueMemberEntity(newData.id))
                userDao.insertUser(member.user.toUserEntity())
            }

            emitAll(localDataFlow)
        }.flowOn(ioDispatcher)
    }

    override suspend fun startQueue(queueId: Int): Result<Unit> {
        return Result.runCatching {
            apiService.startQueue(queueId).getOrThrow()
            val queue = queueAdminDao.getQueue(queueId).first()
            queueAdminDao.updateQueue(queue.copy(isRunning = true))
        }
    }

    override suspend fun stopQueue(queueId: Int): Result<Unit> {
        return Result.runCatching {
            apiService.stopQueue(queueId).getOrThrow()
            val queue = queueAdminDao.getQueue(queueId).first()
            queueAdminDao.updateQueue(queue.copy(isRunning = false))
        }
    }

    override suspend fun restartQueue(queueId: Int): Result<Unit> {
        return Result.runCatching {
            apiService.restartQueue(queueId).getOrThrow()
        }
    }

    override suspend fun inviteUserByNickname(queueId: Int, username: String): Result<Unit> {
        return Result.runCatching {
            apiService.inviteUserByNickname(queueId, username).getOrThrow()
        }
    }

    override suspend fun generateLink(queueId: Int): Result<Unit> {
        return Result.runCatching {
            apiService.generateLink(queueId).getOrThrow()
        }
    }

    override suspend fun deleteLink(queueId: Int): Result<Unit> {
        return Result.runCatching {
            apiService.deleteLink(queueId).getOrThrow()
        }
    }

    override suspend fun randomizeQueue(queueId: Int): Result<Unit> {
        return Result.runCatching {
            apiService.randomizeQueue(queueId).getOrThrow()
        }
    }

    override suspend fun deleteUserFromQueue(queueId: Int, userId: Int): Result<Unit> {
        return Result.runCatching {
            apiService.deleteUserFromQueue(queueId, userId).getOrThrow()
        }
    }

    override suspend fun moveUserInQueue(
        queueId: Int, userId: Int, newPosition: Int
    ): Result<Unit> {
        return Result.runCatching {
            apiService.moveUserInQueue(queueId, userId, newPosition).getOrThrow()
        }
    }

    override suspend fun changeUserReadiness(
        queueId: Int, userId: Int, isReady: Boolean
    ): Result<Unit> {
        return Result.runCatching {
            apiService.changeUserReadiness(queueId, userId, isReady).getOrThrow()
        }
    }
}