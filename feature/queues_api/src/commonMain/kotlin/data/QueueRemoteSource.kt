package data

interface QueueRemoteSource {
    suspend fun deleteQueue(): Result<Unit>
}