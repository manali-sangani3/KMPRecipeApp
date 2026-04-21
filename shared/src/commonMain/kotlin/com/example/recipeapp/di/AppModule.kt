object AppModule {

    private val api by lazy { RecipeApi(HttpClientProvider.client) }

    private val repository by lazy { RecipeRepositoryImpl(api) }

    val getAllRecipeUseCase by lazy {
        GetAllRecipeUseCase(repository)
    }
    val getRecipeUseCase by lazy {
        GetRecipeUseCase(repository)
    }
    val sortRecipesUseCase by lazy {
        SortRecipesUseCase(repository)
    }

    val addRecipeUseCase by lazy {
        AddRecipeUseCase(repository)
    }

    val editRecipeUseCase by lazy {
        EditRecipeUseCase(repository)
    }

    val tagFilterUseCase by lazy {
        TagFilterUseCase(repository)
    }

    val getAllTagsUseCase by lazy {
        GetAllTagsUseCase(repository)
    }

    val deleteRecipeUseCase by lazy {
        DeleteRecipeUseCase(repository)
    }
}