class SortRecipesUseCase(private val repo: RecipeRepository) {
    suspend operator fun invoke(
        order: String,
        limit: Int,
        skip: Int,
        query: String
    ) =
        repo.sortRecipes(order, limit, skip, query)
}