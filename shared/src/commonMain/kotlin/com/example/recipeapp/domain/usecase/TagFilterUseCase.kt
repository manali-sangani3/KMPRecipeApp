class TagFilterUseCase(private val repo: RecipeRepository) {
    suspend operator fun invoke(
        tag: String, limit: Int,
        skip: Int
    ) =
        repo.getRecipesByTag(tag, limit, skip)
}