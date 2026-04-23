import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    private val getRecipeUseCase: GetRecipeUseCase
) : ViewModel() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val _stateRecipe = MutableStateFlow(SingleRecipeUiState(isLoading = true))
    val stateRecipe: StateFlow<SingleRecipeUiState> = _stateRecipe

    fun getRecipe(id: Int) {
        scope.launch {

            val result = getRecipeUseCase(id)

            result.fold(
                onSuccess = { response ->
                    _stateRecipe.value = _stateRecipe.value.copy(
                        recipe = response,
                        isLoading = false
                    )
                },
                onFailure = { error ->
                    _stateRecipe.value = _stateRecipe.value.copy(
                        isLoading = false,
                        error = error.message
                    )
                }
            )
        }
    }
}