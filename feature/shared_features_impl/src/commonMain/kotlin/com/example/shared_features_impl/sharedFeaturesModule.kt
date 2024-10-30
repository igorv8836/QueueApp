package com.example.shared_features_impl

import com.example.common.MyDispatchers
import com.example.shared_features_api.SharedFeaturesNavigator
import com.example.shared_features_api.data.NewsRepository
import com.example.shared_features_api.data.remote.NewsRemoteSource
import com.example.shared_features_impl.data.remote.NewsApiService
import com.example.shared_features_impl.data.repository.NewsRepositoryImpl
import com.example.shared_features_impl.navigation.SharedFeaturesNavigatorImpl
import com.example.shared_features_impl.news_screen.NewsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun sharedFeaturesModule() = module {
    single<SharedFeaturesNavigator> { SharedFeaturesNavigatorImpl() }
    single<NewsRemoteSource> { NewsApiService(get()) }
    single<NewsRepository> { NewsRepositoryImpl(get(), get(), get(named(MyDispatchers.IO))) }
    viewModel { NewsViewModel(get()) }
}