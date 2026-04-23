class SortRecipesUseCase(
    private val repository: RecipeRepository,
) {
    suspend operator fun invoke(
        order: String,
        limit: Int,
        skip: Int,
        query: String
    ) =
        repository.sortRecipes(order, limit, skip, query)
}