package com.example.database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.database.database.AppDatabase
import org.koin.dsl.module

actual fun platformDatabaseBuilderModule() = module {
    single(createdAtStart = true) { provideDatabase() }
}

private fun provideDatabase(): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = documentDirectory() + "/app_database.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath
    )
}

private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null
    )
    return requireNotNull(documentDirectory?.path)
}