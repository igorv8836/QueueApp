package di

import DatabaseTestViewModel
import NetworkTestViewModel
import org.koin.dsl.module

fun testModule() = module {
    factory { DatabaseTestViewModel() }
    factory { NetworkTestViewModel() }
}