package com.example.database

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.example.database.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

actual fun platformDatabaseModule() = module {
    single(createdAtStart = true) { provideDatabase(get()) }
    factory { get<AppDatabase>().getNewsDao() }
}

private fun provideDatabase(context: Context): AppDatabase {
    val dbPath = context.applicationContext.getDatabasePath("app_database.db")
    return Room.databaseBuilder<AppDatabase>(
        context = context.applicationContext,
        name = dbPath.absolutePath
    )
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}