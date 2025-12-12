package dotnet.sort

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.segnities007.ui.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "dotnet",
    ) {
        App()
    }
}