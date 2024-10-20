package com.example.auth.di

import com.example.auth.viewmodel.LoginViewModel
import com.example.auth.viewmodel.RegisterViewModel
import com.example.common.MyDispatchers
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun authFeatureModule() = module {
    viewModel { RegisterViewModel(get(), dispatcher = get(named(MyDispatchers.IO))) }
    viewModel { LoginViewModel(dispatcher = get(named(MyDispatchers.IO)), get()) }
}