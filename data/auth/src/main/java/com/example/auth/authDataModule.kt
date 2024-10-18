package com.example.auth

import com.example.auth.local.TokenManager
import com.example.auth.network.AuthApiService
import com.example.auth.repository.AuthRepository
import com.example.auth.repository.AuthRepositoryImpl
import org.koin.dsl.module


fun authDataModule() = module {
    single<TokenManager> { TokenManager(get()) }
    single<AuthApiService> { AuthApiService(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
}