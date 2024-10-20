package com.example.auth.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.auth.ui.LoginScreen
import com.example.auth.ui.RegisterScreen
import kotlinx.serialization.Serializable


fun NavGraphBuilder.authNavGraph(navController: NavController) {
    composable<LoginRoute> {
        LoginScreen(navController)
    }

    composable<RegisterRoute> {
        RegisterScreen(navController, it.toRoute<RegisterRoute>().email)
    }

    composable<Temp> {
        Text("Main Screen")
    }
}

fun NavController.navigateToRegister(email: String? = null) {
    navigate(RegisterRoute(email))
}

fun NavController.navigateToLogin() {
    navigate(LoginRoute)
}

fun NavController.navigateToMain() {
    navigate(Temp)
}


@Serializable
data object LoginRoute

@Serializable
data class RegisterRoute(val email: String? = null)

@Serializable
data object Temp