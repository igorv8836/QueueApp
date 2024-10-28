package com.example.shared_features_api

import androidx.navigation.*

interface SharedFeaturesNavigator {
    fun registerNavigation(
        navGraphBuilder: NavGraphBuilder,
        mainNavController: NavController,
        bottomNavController: NavController
    )
}
