import kotlinx.serialization.Serializable

enum class SortType {
    NAME_ASC,
    NAME_DESC
}

@Serializable
data class RecipeListResponse(
    val recipes: List<Recipe>,
    val total: Int,
    val skip: Int,
    val limit: Int,
)

@Serializable
data class Recipe(
    val id: Int,
    val name: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val tags: List<String>,
    val prepTimeMinutes: Int,
    val cookTimeMinutes: Int,
    val servings: Int,
    val caloriesPerServing: Int,
    val image: String,
//    val isDeleted: Boolean

)

data class RecipeUiState(
    val recipes: List<Recipe> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val selectedSort: SortType? = null,
    val page: Int = 0,
    val hasMore: Boolean = true,
    val tags: List<String> = emptyList(),
    val selectedTag: String? = null
)

data class SingleRecipeUiState(
    val recipe: Recipe? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
)