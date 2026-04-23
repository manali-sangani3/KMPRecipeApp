class AddRecipeUseCase(
    private val repository: RecipeRepository,
) {
    suspend operator fun invoke(
        request: AddRecipeRequest
    ) =
        repository.addRecipe(
            request
        )
}