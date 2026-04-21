import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddRecipeViewModel(
    private val addRecipeUseCase: AddRecipeUseCase,
    private val editRecipeUseCase: EditRecipeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AddRecipeUiState())
    val state: StateFlow<AddRecipeUiState> = _state

    fun setRecipeForEdit(recipe: Recipe) {
        _state.value = _state.value.copy(
            name = recipe.name,
            ingredients = recipe.ingredients,
            instructions = recipe.instructions,
            prepTimeMin = formatTime(recipe.prepTimeMinutes),
            cookTimeMin = formatTime(recipe.cookTimeMinutes),
            servings = recipe.servings.toString(),
            caloriesPerServing = "${recipe.caloriesPerServing} kcal",
            image = recipe.image
        )
    }

    fun onNameChange(value: String) {
        _state.value = _state.value.copy(name = value)
    }

    fun onIngredientChange(index: Int, value: String) {
        val list = _state.value.ingredients.toMutableList()
        list[index] = value
        _state.value = _state.value.copy(ingredients = list)
    }

    fun addIngredient() {
        _state.value = _state.value.copy(
            ingredients = _state.value.ingredients + ""
        )
    }

    fun removeIngredient(index: Int) {
        val list = _state.value.ingredients.toMutableList()

        if (list.size > 1) {
            list.removeAt(index)
        }

        _state.value = _state.value.copy(ingredients = list)
    }

    fun onInstructionChange(index: Int, value: String) {
        val list = _state.value.instructions.toMutableList()
        list[index] = value
        _state.value = _state.value.copy(instructions = list)
    }

    fun addInstruction() {
        _state.value = _state.value.copy(
            instructions = _state.value.instructions + ""
        )
    }

    fun removeInstruction(index: Int) {
        val list = _state.value.instructions.toMutableList()

        if (list.size > 1) {
            list.removeAt(index)
        }

        _state.value = _state.value.copy(instructions = list)
    }

    fun onCookTimeHourChange(value: String) {
        _state.value = _state.value.copy(cookTimeHour = value)
    }

    fun onCookTimeMinChange(value: String) {
        _state.value = _state.value.copy(cookTimeMin = value)
    }

    fun onPrepTimeHourChange(value: String) {
        _state.value = _state.value.copy(prepTimeHour = value)
    }

    fun onPrepTimeMinChange(value: String) {
        _state.value = _state.value.copy(prepTimeMin = value)
    }

    fun addServings(value: String) {
        _state.value = _state.value.copy(servings = value)
    }

    fun addCalories(value: String) {
        _state.value = _state.value.copy(caloriesPerServing = value)
    }

    fun saveRecipe() {
        viewModelScope.launch {

            val current = _state.value

            // 🔴 VALIDATION
            if (current.name.isBlank()) {
                _state.value = current.copy(error = "Name required")
                return@launch
            }

            _state.value = current.copy(
                isLoading = true,
                error = null
            )

            val result = addRecipeUseCase(
                AddRecipeRequest(
                    name = current.name,
                    ingredients = current.ingredients.filter { it.isNotBlank() },
                    instructions = current.instructions.filter { it.isNotBlank() },
                    prepTimeMinutes = current.prepTimeMin.toIntOrNull() ?: 0,
                    cookTimeMinutes = current.cookTimeMin.toIntOrNull() ?: 0,
                    servings = 1,
                    caloriesPerServing = 0,
                    image = current.image.ifEmpty { "https://cdn.dummyjson.com/recipe-images/1.webp" },
                )
            )

            result.fold(
                onSuccess = {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isSuccess = true
                    )
                },
                onFailure = {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = it.message
                    )
                }
            )
        }
    }

    fun updateRecipe(id: Int) {
        viewModelScope.launch {

            val current = _state.value

            _state.value = current.copy(isLoading = true)

            val result = editRecipeUseCase(
                AddRecipeRequest(
                    name = current.name,
                    ingredients = current.ingredients,
                    instructions = current.instructions,
                    prepTimeMinutes = current.prepTimeMin.toIntOrNull() ?: 0,
                    cookTimeMinutes = current.cookTimeMin.toIntOrNull() ?: 0,
                    servings = 1,
                    caloriesPerServing = 0,
                    image = current.image
                ),
                id = id
            )

            result.fold(
                onSuccess = {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isSuccess = true
                    )
                },
                onFailure = {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = it.message
                    )
                }
            )
        }
    }

    private fun formatTime(minutes: Int): String {
        val hours = minutes / 60
        val mins = minutes % 60

        return when {
            hours > 0 && mins > 0 -> "$hours hr $mins min"
            hours > 0 -> "$hours hr"
            else -> "$mins min"
        }
    }
}