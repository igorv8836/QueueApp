package com.example.ui_common.navigation

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
data object MainScreenRoute


fun NavController.navigateToMain() {
    navigate(MainScreenRoute)
}