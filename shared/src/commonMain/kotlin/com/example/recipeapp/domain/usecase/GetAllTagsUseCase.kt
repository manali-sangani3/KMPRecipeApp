class GetAllTagsUseCase(
    private val repository: RecipeRepository,
) {
    suspend operator fun invoke(): Result<List<String>> {
        return repository.getAllTags()
    }
}