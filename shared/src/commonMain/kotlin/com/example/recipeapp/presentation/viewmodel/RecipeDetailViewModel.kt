import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    private val getRecipeUseCase: GetRecipeUseCase
) : ViewModel() {

    private val _stateRecipe = MutableStateFlow(SingleRecipeUiState(isLoading = true))
    val stateRecipe: StateFlow<SingleRecipeUiState> = _stateRecipe

    fun getRecipe(id: Int) {
        viewModelScope.launch {

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
    fun onEditRecipe(recipe: Recipe) {
        // later you can open dialog or navigate
    }

    fun clearSelectedRecipe() {
        _stateRecipe.value = SingleRecipeUiState()
    }
}