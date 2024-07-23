package com.example.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.dsl.withOptions
import org.koin.core.qualifier.named
import org.koin.dsl.module

val commonModule = module {
    single { provideIoDispatcher() } withOptions {
        named(MyDispatchers.IO)
    }

    single { provideDefaultDispatcher() } withOptions {
        named(MyDispatchers.Default)
    }

    single {
        provideApplicationScope(
            get(named(MyDispatchers.Default))
        )
    }

}

internal fun provideIoDispatcher() = Dispatchers.IO
internal fun provideDefaultDispatcher() = Dispatchers.Default
internal fun provideApplicationScope(dispatcher: CoroutineDispatcher): CoroutineScope =
    CoroutineScope(SupervisorJob() + dispatcher)


enum class MyDispatchers {
    IO, Default
}