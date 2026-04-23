class GetRecipeUseCase(
    private val repository: RecipeRepository,
) {
    suspend operator fun invoke(id: Int) =
        repository.getRecipe(id = id)
}