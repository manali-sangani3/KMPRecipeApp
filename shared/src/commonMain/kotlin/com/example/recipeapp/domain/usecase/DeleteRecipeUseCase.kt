class DeleteRecipeUseCase(private val repo: RecipeRepository) {
    suspend operator fun invoke(
        id: Int
    ) =
        repo.deleteRecipe(
            id
        )
}