package com.example.ui_common.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem<T : Any>(
    val title: String,
    val route: T,
    val icon: ImageVector
)