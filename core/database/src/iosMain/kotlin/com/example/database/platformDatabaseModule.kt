package com.example.database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.example.database.database.AppDatabase
import com.example.database.database.instantiateImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory

actual fun platformDatabaseModule() = module {
    single(createdAtStart = true) { provideDatabase() }
    factory { get<AppDatabase>().getNewsDao() }
}

private fun provideDatabase(): AppDatabase {
    val dbFilePath = NSHomeDirectory() + "/app_database.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath,
        factory = { AppDatabase::class.instantiateImpl() }
    )
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}