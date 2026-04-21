import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import cafe.adriel.voyager.navigator.Navigator

@Composable
fun AppRoot() {
    var showSplash by remember { mutableStateOf(true) }

    if (showSplash) {
        SplashScreen(
            onTimeout = { showSplash = false }
        )
    } else {
        Navigator(HomeScreen())
    }
}