import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.encodedPath

class RecipeApi(private val client: HttpClient) {


    suspend fun getAllRecipes(
        limit: Int,
        skip: Int,
        query: String? = null
    ): RecipeListResponse {
        return client.get("https://dummyjson.com/recipes") {
            contentType(ContentType.Application.Json)
            parameter("limit", limit)
            parameter("skip", skip)
            if (!query.isNullOrEmpty()) {
                url {
                    encodedPath = "/recipes/search"
                }
                parameter("q", query)
            }

        }.body()
    }


    suspend fun getRecipe(id: Int): Recipe {
        return client.get("https://dummyjson.com/recipes/$id") {
            contentType(ContentType.Application.Json)
        }.body()
    }

    suspend fun sortRecipes(
        order: String,
        limit: Int,
        skip: Int,
        query: String? = null
    ): RecipeListResponse {
        return client.get("https://dummyjson.com/recipes") {
            contentType(ContentType.Application.Json)
            parameter("limit", limit)
            parameter("skip", skip)
            if (!query.isNullOrEmpty()) {
                url {
                    encodedPath = "/recipes/search"
                }
                parameter("q", query)
            }
            parameter("sortBy", "name")
            parameter("order", order)
        }.body()
    }

    suspend fun addRecipe(
        request: AddRecipeRequest
    ): Recipe {
        return client.post("https://dummyjson.com/recipes/add") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    suspend fun editRecipe(
        request: AddRecipeRequest,
        id: Int

    ): Recipe {
        return client.patch("https://dummyjson.com/recipes/$id") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    suspend fun getAllTags(): List<String> {
        return client.get("https://dummyjson.com/recipes/tags").body()
    }

    suspend fun getRecipesByTag(
        tag: String,
        limit: Int,
        skip: Int
    ): RecipeListResponse {
        return client.get("https://dummyjson.com/recipes/tag/$tag") {
            contentType(ContentType.Application.Json)
            parameter("limit", limit)
            parameter("skip", skip)
        }.body()
    }

    suspend fun deleteRecipe(
        id: Int
    ): Recipe {
        return client.delete("https://dummyjson.com/recipes/$id") {
            contentType(ContentType.Application.Json)
        }.body()
    }

}