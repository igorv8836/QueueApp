@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.example.database.database

import androidx.room.*
import com.example.database.dao.NewsDao
import com.example.database.model.NewsEntity

@Database(
    entities = [
        NewsEntity::class
    ],
    version = 1
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getNewsDao(): NewsDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor: RoomDatabaseConstructor<AppDatabase>