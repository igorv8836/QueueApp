package di

import DataStoreViewModel
import DatabaseTestViewModel
import NetworkTestViewModel
import org.koin.dsl.module

fun testModule() = module {
    factory { DatabaseTestViewModel() }
    factory { NetworkTestViewModel() }
    factory { DataStoreViewModel() }
}