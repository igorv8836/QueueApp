@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.example.database.database

import androidx.room.*
import com.example.database.dao.*
import com.example.database.model.*

@Database(
    entities = [
        NewsEntity::class,
        QueueAdminEntity::class,
        QueueMemberEntity::class,
        UserEntity::class
    ],
    version = 1
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao

    abstract fun getQueueAdminDao(): QueueAdminDao
    abstract fun getQueueMemberDao(): QueueMemberDao
    abstract fun getUserDao(): UserDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase>