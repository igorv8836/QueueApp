package com.example.auth_impl.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.auth_api.navigation.*
import com.example.auth_impl.ui.*

internal class AuthNavigatorImpl : AuthNavigator {
    override fun registerNavigation(
        navGraphBuilder: NavGraphBuilder,
        mainNavController: NavController
    ) {
        navGraphBuilder.apply {
            composable<LoginRoute> {
                LoginScreen(navController = mainNavController)
            }

            composable<RegisterRoute> {
                RegisterScreen(mainNavController, it.toRoute<RegisterRoute>().email)
            }

            composable<SplashRoute> {
                SplashScreen(navController = mainNavController)
            }
        }
    }
}
