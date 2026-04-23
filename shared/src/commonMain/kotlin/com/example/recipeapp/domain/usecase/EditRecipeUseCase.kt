class EditRecipeUseCase(
    private val repository: RecipeRepository,
) {
    suspend operator fun invoke(
        request: AddRecipeRequest,
        id: Int
    ) =
        repository.editRecipe(
            request, id
        )
}