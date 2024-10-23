package com.example.auth.navigation

import androidx.compose.material3.Text
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