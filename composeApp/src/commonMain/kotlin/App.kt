
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.auth.navigation.LoginRoute
import com.example.auth.navigation.authNavGraph
import com.example.ui_theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    AppTheme {
        val navController = rememberNavController()


        NavHost(navController = navController, startDestination = LoginRoute) {
            authNavGraph(navController)
        }
    }
}