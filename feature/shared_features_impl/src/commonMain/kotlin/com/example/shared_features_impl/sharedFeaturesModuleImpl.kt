package com.example.shared_features_impl

import com.example.shared_features_api.SharedFeaturesNavigator
import com.example.shared_features_impl.navigation.SharedFeaturesNavigatorImpl
import org.koin.dsl.module

fun sharedFeaturesModuleImpl() = module {
    single<SharedFeaturesNavigator> { SharedFeaturesNavigatorImpl() }
}