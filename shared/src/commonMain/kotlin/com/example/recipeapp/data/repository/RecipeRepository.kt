class RecipeRepositoryImpl(
    private val api: RecipeApi
) : RecipeRepository {


    override suspend fun getAllRecipes(
        limit: Int,
        skip: Int,
        query: String
    ): Result<RecipeListResponse> {
        return try {
            val response = api.getAllRecipes(limit, skip, query)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRecipe(id: Int): Result<Recipe> {
        return try {
            val response = api.getRecipe(id)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sortRecipes(
        order: String,
        limit: Int,
        skip: Int,
        query: String
    ): Result<RecipeListResponse> {
        return try {
            val response = api.sortRecipes(order, limit, skip, query)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addRecipe(
        request: AddRecipeRequest
    ): Result<Recipe> {
        return try {
            val response = api.addRecipe(
                request
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun editRecipe(
        request: AddRecipeRequest,
        id: Int
    ): Result<Recipe> {
        return try {
            val response = api.editRecipe(
                request, id
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllTags(): Result<List<String>> {
        return try {
            val response = api.getAllTags()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRecipesByTag(
        tag: String, limit: Int,
        skip: Int
    ): Result<RecipeListResponse> {
        return try {
            val response = api.getRecipesByTag(tag, limit, skip)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteRecipe(
        id: Int
    ): Result<Recipe> {
        return try {
            val response = api.deleteRecipe(
                id
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}