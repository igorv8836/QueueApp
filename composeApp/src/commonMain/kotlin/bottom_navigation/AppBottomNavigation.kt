package bottom_navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.shared_features_api.*
import org.koin.compose.koinInject

val bottomRoutes = listOf(
    getNewsBottomItem(), getSettingsBottomItem()
)

@Composable
fun AppBottomNavigation(
    mainNavController: NavHostController, bottomNavController: NavHostController
) {
    Scaffold(bottomBar = {
        NavigationBar {
            val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            bottomRoutes.forEach { topLevelRoute ->
                NavigationBarItem(icon = {
                    Icon(
                        topLevelRoute.icon, contentDescription = topLevelRoute.title
                    )
                },
                    label = { Text(topLevelRoute.title) },
                    selected = currentDestination?.hierarchy?.any { it.hasRoute(topLevelRoute.route::class) } == true,
                    onClick = {
                        bottomNavController.navigate(topLevelRoute.route) {
                            popUpTo(bottomNavController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    })
            }
        }
    }) {
        val sharedFeaturesNavigator: SharedFeaturesNavigator = koinInject<SharedFeaturesNavigator>()
        NavHost(
            navController = bottomNavController,
            startDestination = NewsScreenRoute,
            modifier = Modifier.padding(it)
        ) {
            sharedFeaturesNavigator.registerNavigation(this, mainNavController, bottomNavController)
        }
    }
}