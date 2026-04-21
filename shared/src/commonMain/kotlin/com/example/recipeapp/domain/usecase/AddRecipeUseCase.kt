class AddRecipeUseCase(private val repo: RecipeRepository) {
    suspend operator fun invoke(
        request: AddRecipeRequest
    ) =
        repo.addRecipe(
            request
        )
}