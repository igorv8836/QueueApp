package com.example.auth

import com.example.auth.network.AuthApiService
import com.example.auth.network.RemoteDataSource
import com.example.auth.repository.AuthRepository
import com.example.auth.repository.AuthRepositoryImpl
import com.example.datastore.TokenManager
import org.koin.dsl.module


fun authDataModule() = module {
    single<TokenManager> { TokenManager(get()) }
    single<RemoteDataSource> { AuthApiService(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
}