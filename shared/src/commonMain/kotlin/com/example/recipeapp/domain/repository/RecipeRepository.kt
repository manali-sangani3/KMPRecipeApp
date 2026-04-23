interface RecipeRepository {

    suspend fun getAllRecipes(
        limit: Int,
        skip: Int,
        query: String
    ): Result<RecipeListResponse>

    suspend fun getRecipe(
        id: Int
    ): Result<Recipe>

    suspend fun sortRecipes(
        order: String,
        limit: Int,
        skip: Int,
        query: String
    ): Result<RecipeListResponse>

    suspend fun addRecipe(
        request: AddRecipeRequest
    ): Result<Recipe>

    suspend fun editRecipe(
        request: AddRecipeRequest,
        id: Int
    ): Result<Recipe>

    suspend fun getAllTags(
    ): Result<List<String>>

    suspend fun getRecipesByTag(
        tag: String,
        limit: Int,
        skip: Int
    ): Result<RecipeListResponse>

    suspend fun deleteRecipe(
        id: Int
    ): Result<Recipe>

}