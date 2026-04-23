import kotlinx.coroutines.CoroutineDispatcher

class GetAllRecipeUseCase(
    private val repository: RecipeRepository,
) {
    suspend operator fun invoke(
        limit: Int,
        skip: Int,
        query: String
    ) =
        repository.getAllRecipes(limit, skip, query)
}