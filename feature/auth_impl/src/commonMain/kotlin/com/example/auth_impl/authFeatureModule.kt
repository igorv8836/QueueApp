package com.example.auth_impl

import com.example.auth_api.data.*
import com.example.auth_api.navigation.AuthNavigator
import com.example.auth_impl.data.*
import com.example.auth_impl.navigation.AuthNavigatorImpl
import com.example.auth_impl.viewmodel.*
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun authFeatureModule() = module {
    viewModel { RegisterViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { SplashViewModel(get()) }
    single<RemoteDataSource> { AuthApiService(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<AuthNavigator> { AuthNavigatorImpl() }
}