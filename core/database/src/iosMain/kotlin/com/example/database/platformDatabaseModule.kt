package com.example.database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.database.database.AppDatabase
import com.example.database.database.instantiateImpl
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual fun platformDatabaseBuilderModule() = module {
    single(createdAtStart = true) { provideDatabase() }
}

private fun provideDatabase(): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = documentDirectory() + "/app_database.db"
    return Room.databaseBuilder(
        name = dbFilePath,
        factory = { AppDatabase::class.instantiateImpl() }
    )
}

@OptIn(ExperimentalForeignApi::class)
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