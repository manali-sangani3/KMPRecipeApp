class TagFilterUseCase(
    private val repository: RecipeRepository,
) {
    suspend operator fun invoke(
        tag: String, limit: Int,
        skip: Int
    ) =
        repository.getRecipesByTag(tag, limit, skip)
}