class DeleteRecipeUseCase(
    private val repository: RecipeRepository,
) {
    suspend operator fun invoke(
        id: Int
    ) =
        repository.deleteRecipe(
            id
        )
}