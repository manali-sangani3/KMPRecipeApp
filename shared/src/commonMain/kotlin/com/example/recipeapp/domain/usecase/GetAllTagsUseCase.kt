class GetAllTagsUseCase(private val repo: RecipeRepository) {
    suspend operator fun invoke(): Result<List<String>> {
        return repo.getAllTags()
    }
}