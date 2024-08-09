package com.example.datastore

import android.content.Context
import org.koin.dsl.module

actual fun createDataStoreModule() = module {
    single { createDataStore(get<Context>()) }
}