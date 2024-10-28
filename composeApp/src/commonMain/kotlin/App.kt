
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import bottom_navigation.AppBottomNavigation
import com.example.auth.navigation.*
import com.example.ui_common.AppTheme
import com.example.ui_common.navigation.MainScreenRoute
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    AppTheme {
        val navController = rememberNavController()
        val bottomNavController = rememberNavController()

        NavHost(navController = navController, startDestination = SplashRoute) {
            authNavGraph(navController)

            composable<MainScreenRoute> {
                AppBottomNavigation(
                    mainNavController = navController,
                    bottomNavController = bottomNavController
                )
            }
        }
    }
}