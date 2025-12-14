package dotnet.sort

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dotnet.sort.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "dotnet",
    ) {
        App()
    }
}