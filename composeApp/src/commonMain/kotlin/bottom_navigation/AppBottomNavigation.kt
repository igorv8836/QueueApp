package bottom_navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
        Column {
            HorizontalDivider(
                color = Color.Gray,
                thickness = 1.dp
            )
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                bottomRoutes.forEach { topLevelRoute ->
                    val isSelected =
                        currentDestination?.hierarchy?.any { it.hasRoute(topLevelRoute.route::class) } == true
                    NavigationBarItem(icon = {
                        Icon(
                            topLevelRoute.icon,
                            contentDescription = topLevelRoute.title,
                            tint = if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }, label = {
                        Text(
                            topLevelRoute.title,
                            color = if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }, colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ), selected = isSelected, onClick = {
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