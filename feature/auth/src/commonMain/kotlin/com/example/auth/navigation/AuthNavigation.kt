package com.example.auth.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.auth.ui.*
import kotlinx.serialization.Serializable


fun NavGraphBuilder.authNavGraph(navController: NavController) {
    composable<LoginRoute> {
        LoginScreen(navController = navController)
    }

    composable<RegisterRoute> {
        RegisterScreen(navController, it.toRoute<RegisterRoute>().email)
    }

    composable<SplashRoute> {
        SplashScreen(navController = navController)
    }
}

fun NavController.navigateToRegister(email: String? = null) {
    navigate(RegisterRoute(email))
}

fun NavController.navigateToLogin() {
    navigate(LoginRoute)
}

@Serializable
data object LoginRoute

@Serializable
data object SplashRoute

@Serializable
data class RegisterRoute(val email: String? = null)