package com.example.shared_features_api

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.navigation.*
import com.example.ui_common.navigation.BottomNavItem
import kotlinx.serialization.Serializable

interface SharedFeaturesNavigator {
    fun registerNavigation(
        navGraphBuilder: NavGraphBuilder,
        mainNavController: NavController,
        bottomNavController: NavController
    )
}

@Serializable
data object NewsScreenRoute

@Serializable
data object SettingsScreenRoute

fun NavController.navigateToNews() = navigate(NewsScreenRoute)
fun NavController.navigateToSettings() = navigate(SettingsScreenRoute)

fun getNewsBottomItem() = BottomNavItem(
    route = NewsScreenRoute, icon = Icons.Default.Newspaper, title = "Новости"
)

fun getSettingsBottomItem() = BottomNavItem(
    route = SettingsScreenRoute, icon = Icons.Default.Settings, title = "Настройки"
)