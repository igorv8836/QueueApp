package com.example.datastore

import org.koin.core.module.Module
import org.koin.dsl.module

fun datastoreModule() = module {
    includes(createDataStoreModule())

    single { TokenManager(get()) }
}


expect fun createDataStoreModule(): Module