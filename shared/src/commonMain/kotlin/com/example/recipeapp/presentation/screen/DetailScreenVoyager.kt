import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator

class DetailScreenVoyager(
    private val recipeId: Int
) : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current

        val viewModel = remember {
            RecipeDetailViewModel(AppModule.getRecipeUseCase)
        }

        val state by viewModel.stateRecipe.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.getRecipe(recipeId)
        }

        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize().background(
                        color = Color.White
                    ),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }

            state.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Error: ${state.error}")
                }
            }

            state.recipe != null -> {
                RecipeDetailScreen(
                    recipe = state.recipe!!,
                    onBack = { navigator?.pop() }
                )
            }
        }
    }
}