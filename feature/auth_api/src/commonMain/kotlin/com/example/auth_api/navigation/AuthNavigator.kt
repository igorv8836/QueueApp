package com.example.auth_api.navigation

import androidx.navigation.*
import kotlinx.serialization.Serializable

interface AuthNavigator {
    fun registerNavigation(
        navGraphBuilder: NavGraphBuilder,
        mainNavController: NavController
    )
}

@Serializable
data object LoginRoute

@Serializable
data object SplashRoute

@Serializable
data class RegisterRoute(val email: String? = null)

fun NavController.navigateToLogin() = navigate(LoginRoute)
fun NavController.navigateToRegister(email: String?) = navigate(RegisterRoute(email))