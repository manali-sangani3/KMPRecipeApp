import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAllRecipeUseCase: GetAllRecipeUseCase,
    private val sortRecipesUseCase: SortRecipesUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val getRecipesByTagUseCase: TagFilterUseCase,
    private val getAllTagsUseCase: GetAllTagsUseCase,
) : ViewModel(), ScreenModel {

    private val _state = MutableStateFlow(RecipeUiState(isLoading = true))
    private val _stateRecipe = MutableStateFlow(SingleRecipeUiState(isLoading = true))
    private val _stateDeleteRecipe = MutableStateFlow(SingleRecipeUiState(isLoading = true))
    val state: StateFlow<RecipeUiState> = _state
    val stateRecipe: StateFlow<SingleRecipeUiState> = _stateRecipe
    val stateDeleteRecipe: StateFlow<SingleRecipeUiState> = _stateDeleteRecipe
    private val pageSize = 10
    private var searchJob: Job? = null


    init {
        loadTags()
        loadRecipes()
    }

    private fun loadTags() {
        viewModelScope.launch {

            val result = getAllTagsUseCase()

            result.fold(
                onSuccess = { tags ->
                    _state.value = _state.value.copy(
                        tags = tags,
                        error = null
                    )
                },
                onFailure = {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isLoadingMore = false,
                        error = it.message ?: "Something went wrong"
                    )
                }
            )
        }
    }

    fun loadRecipes(
        isLoadMore: Boolean = false
    ) {
        viewModelScope.launch {

            val currentState = _state.value
            val query = currentState.searchQuery

            if (isLoadMore && (currentState.isLoadingMore || !currentState.hasMore)) return@launch

            _state.value = currentState.copy(
                isLoading = !isLoadMore,
                isLoadingMore = isLoadMore,
                error = null
            )

            val nextPage = if (isLoadMore) currentState.page + 1 else 0

            val result = when {

                currentState.selectedTag != null -> {
                    getRecipesByTagUseCase(
                        tag = currentState.selectedTag,
                        limit = pageSize,
                        skip = nextPage * pageSize
                    )
                }

                currentState.selectedSort != null -> {
                    val order =
                        if (currentState.selectedSort == SortType.NAME_ASC) "asc" else "desc"

                    sortRecipesUseCase(
                        order = order,
                        limit = pageSize,
                        skip = nextPage * pageSize,
                        query = query
                    )
                }

                else -> {
                    getAllRecipeUseCase(
                        limit = pageSize,
                        skip = nextPage * pageSize,
                        query = query
                    )
                }
            }

            result.fold(
                onSuccess = { response ->
                    val latestState = _state.value
                    val newList = if (isLoadMore) {
                        latestState.recipes + response.recipes
                    } else {
                        response.recipes
                    }
                    _state.value = latestState.copy(
                        recipes = newList,
                        isLoading = false,
                        isLoadingMore = false,
                        page = nextPage,
                        hasMore = response.recipes.isNotEmpty(),
                    )
                },
                onFailure = {
                    _state.value = currentState.copy(
                        isLoading = false,
                        isLoadingMore = false,
                        error = it.message
                    )
                }
            )
        }
    }

    fun onTagSelected(tag: String?) {

        _state.value = _state.value.copy(
            selectedTag = tag,
            selectedSort = null,
            page = 0,
            hasMore = true,
            recipes = emptyList()
        )

        loadRecipes()
    }

    fun onSortSelected(sortType: SortType) {

        _state.value = _state.value.copy(
            selectedSort = sortType,
            page = 0,
            hasMore = true,
            recipes = emptyList()
        )

        loadRecipes(isLoadMore = false)
    }

    fun resetList() {

        _state.value = _state.value.copy(
            selectedSort = null,
            page = 0,
            searchQuery = "",
            hasMore = true,
            recipes = emptyList()
        )

        loadRecipes()
    }

    fun onSearchChange(query: String) {

        _state.value = _state.value.copy(
            searchQuery = query,
            page = 0,
            hasMore = true
        )

        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            kotlinx.coroutines.delay(400)

            _state.value = _state.value.copy(
                recipes = emptyList()
            )

            loadRecipes()
        }
    }

    fun onDeleteRecipe(id: Int) {
        viewModelScope.launch {

            val result = deleteRecipeUseCase(id)

            result.fold(
                onSuccess = { response ->
                    _stateDeleteRecipe.value = _stateDeleteRecipe.value.copy(
                        recipe = response,
                        isLoading = false
                    )
                },
                onFailure = { error ->
                    _stateDeleteRecipe.value = _stateDeleteRecipe.value.copy(
                        isLoading = false,
                        error = error.message
                    )
                }
            )
        }
    }

    fun resetDeleteState() {
        _stateDeleteRecipe.value = SingleRecipeUiState()
    }

    fun clearSelectedRecipe() {
        _stateRecipe.value = SingleRecipeUiState()
    }
}