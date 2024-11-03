import androidx.compose.ui.window.*
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