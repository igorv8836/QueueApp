
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import bottom_navigation.AppBottomNavigation
import com.example.auth_api.navigation.AuthNavigator
import com.example.auth_api.navigation.SplashRoute
import com.example.ui_common.AppTheme
import com.example.ui_common.navigation.MainScreenRoute
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject


@Composable
@Preview
fun App() {
    AppTheme {
        val navController = rememberNavController()
        val bottomNavController = rememberNavController()
        val sharedFeaturesNavigator: AuthNavigator = koinInject<AuthNavigator>()


        NavHost(navController = navController, startDestination = SplashRoute) {
            sharedFeaturesNavigator.registerNavigation(this, mainNavController = navController)

            composable<MainScreenRoute> {
                AppBottomNavigation(
                    mainNavController = navController,
                    bottomNavController = bottomNavController
                )
            }
        }
    }
}