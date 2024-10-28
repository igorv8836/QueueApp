package com.example.auth.di

import com.example.auth.viewmodel.*
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun authFeatureModule() = module {
    viewModel { RegisterViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { SplashViewModel(get()) }
}