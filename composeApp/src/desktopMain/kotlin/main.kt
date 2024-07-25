import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.KoinFactory

fun main() {
    KoinFactory.setupKoin()
    return application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "QueueApp",
        ) {
            App()
        }
    }
}