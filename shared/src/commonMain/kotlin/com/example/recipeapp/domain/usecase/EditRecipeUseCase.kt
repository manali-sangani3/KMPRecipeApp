class EditRecipeUseCase(private val repo: RecipeRepository) {
    suspend operator fun invoke(
        request: AddRecipeRequest,
        id: Int
    ) =
        repo.editRecipe(
            request, id
        )
}