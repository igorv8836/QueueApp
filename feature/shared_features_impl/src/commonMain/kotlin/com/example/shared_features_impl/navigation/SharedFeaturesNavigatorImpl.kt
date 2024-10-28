package com.example.shared_features_impl.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.shared_features_api.*
import com.example.shared_features_impl.news_screen.NewsScreen
import com.example.shared_features_impl.settings_screen.SettingsScreen

internal class SharedFeaturesNavigatorImpl : SharedFeaturesNavigator {
    override fun registerNavigation(
        navGraphBuilder: NavGraphBuilder,
        mainNavController: NavController,
        bottomNavController: NavController
    ) {
        navGraphBuilder.apply {
            composable<NewsScreenRoute> {
                NewsScreen()
            }
            composable<SettingsScreenRoute> {
                SettingsScreen()
            }
        }
    }
}
