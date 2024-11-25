package com.example.database.dao

import androidx.room.*
import com.example.database.model.QueueMemberEntity

@Dao
interface QueueMemberDao {

    @Insert
    suspend fun insertMember(member: QueueMemberEntity)

    @Query("DELETE FROM queue_member WHERE id = :id")
    suspend fun deleteMember(id: Int)

    @Update
    suspend fun updateMember(member: QueueMemberEntity)

    @Query("SELECT * FROM queue_member WHERE id = :id")
    suspend fun getMember(id: Int): QueueMemberEntity?

    @Query("SELECT * FROM queue_member WHERE queueId = :queueId")
    suspend fun getMembersByQueueId(queueId: Int): List<QueueMemberEntity>

    @Query("DELETE FROM queue_member WHERE queueId = :queueId")
    suspend fun deleteMembersByQueueId(queueId: Int)
}