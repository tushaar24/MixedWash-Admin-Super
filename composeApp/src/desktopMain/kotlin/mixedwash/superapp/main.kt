package mixedwash.superapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import mixedwash.superapp.features.orders.presentation.OrdersDashboardScreen

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "MixedWashSuperApp",
    ) {
        OrdersDashboardScreen(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xff1E1E1E))
        )
    }
}