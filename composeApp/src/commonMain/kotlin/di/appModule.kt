package di

import TestViewModel
import org.koin.dsl.module

fun testModule() = module {
    factory { TestViewModel() }
}