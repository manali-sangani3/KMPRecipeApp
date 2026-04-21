class GetRecipeUseCase(private val repo: RecipeRepository) {
    suspend operator fun invoke(id: Int) =
        repo.getRecipe(id = id)
}