import kotlinx.serialization.Serializable

@Serializable
data class AddRecipeRequest(
    val name: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val prepTimeMinutes: Int,
    val cookTimeMinutes: Int,
    val servings: Int,
    val caloriesPerServing: Int,
    val image: String
)

data class AddRecipeUiState(
    val name: String = "",
    val tags: String = "",
    val ingredients: List<String> = listOf(""),
    val instructions: List<String> = listOf(""),
    val image: String = "",
    val link: String = "",
    val cookTimeHour: String = "",
    val cookTimeMin: String = "",
    val prepTimeHour: String = "",
    val prepTimeMin: String = "",
    val servings: String="",
    val caloriesPerServing: String="",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)