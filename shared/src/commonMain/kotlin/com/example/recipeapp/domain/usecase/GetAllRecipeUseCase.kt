class GetAllRecipeUseCase(private val repo: RecipeRepository) {
    suspend operator fun invoke(
        limit: Int,
        skip: Int,
        query: String
    ) =
        repo.getAllRecipes(limit, skip,query)
}