import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator


class HomeScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current

        val viewModel = rememberScreenModel {
            HomeViewModel(
                AppModule.getAllRecipeUseCase,
                AppModule.sortRecipesUseCase,
                AppModule.deleteRecipeUseCase,
                AppModule.tagFilterUseCase,
                AppModule.getAllTagsUseCase,
            )
        }
        val state by viewModel.state.collectAsState()
        val stateRecipe by viewModel.stateRecipe.collectAsState()
        val stateDeleteRecipe by viewModel.stateDeleteRecipe.collectAsState()
        var showBottomSheet by remember { mutableStateOf(false) }
        val listState = rememberLazyListState()
        val snackbarHostState = remember { SnackbarHostState() }

        Scaffold(
            contentWindowInsets = WindowInsets.safeDrawing,
            containerColor = Color.White,
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            topBar = {
                Column(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(start = 24.dp).verticalScroll(
                            rememberScrollState()
                        )
                ) {
                    Text(
                        text = "Tasty Recipes",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text = "for you.",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Gray,
                    )
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.primary,
                    shape = CircleShape,
                    content = {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = null,
                            tint = Color.White,
                        )
                    },
                    onClick = {
                        navigator?.push(AddEditRecipeScreenVoyager())
                    })
            }) { paddingValues ->
            Column(modifier = Modifier.padding(16.dp).padding(paddingValues)) {
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    AppTextField(
                        value = state.searchQuery,
                        onValueChange = { viewModel.onSearchChange(it) },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(12.dp))
                    IconBg(
                        content = "Sort",
                        icon = Icons.AutoMirrored.Default.Sort,
                        size = 52.dp,
                        onClick = { showBottomSheet = true })
                }

                Spacer(Modifier.height(16.dp))

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    item {
                        TagChip(
                            text = "All",
                            isSelected = state.selectedTag == null,
                            onClick = { viewModel.onTagSelected(null) }
                        )
                    }

                    items(state.tags) { tag ->
                        println("state.tags=================${state.tags}")
                        TagChip(
                            text = tag,
                            isSelected = tag == state.selectedTag,
                            onClick = { viewModel.onTagSelected(tag) }
                        )
                    }
                }

                if (state.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                state.error?.let {
                    Text("Error: $it")
                }

                if (state.recipes.isEmpty() && state.searchQuery.isNotEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No results found")
                    }
                }

                LaunchedEffect(listState) {
                    snapshotFlow {
                        listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                    }.collect { lastIndex ->

                        val total = state.recipes.size

                        if (lastIndex != null) {
                            if (lastIndex >= total - 2) {
                                viewModel.loadRecipes(
                                    isLoadMore = true
                                )
                            }
                        }
                    }
                }
                LaunchedEffect(stateDeleteRecipe.isSuccess) {
                    if (stateDeleteRecipe.isSuccess) {
                        snackbarHostState.showSnackbar(
                            message = "Recipe deleted successfully",

                            duration = SnackbarDuration.Short
                        )
                        viewModel.resetDeleteState()
                    }
                }
                if (stateRecipe.recipe != null) {
                    RecipeDetailScreen(
                        recipe = stateRecipe.recipe!!,
                        onBack = { viewModel.clearSelectedRecipe() })
                } else {
                    LazyColumn(
                        state = listState,
                        contentPadding = PaddingValues(bottom = 50.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        items(state.recipes) { recipe ->
                            RecipeItem(
                                recipe = recipe,
                                searchQuery = state.searchQuery,
                                onClick = {
                                    navigator?.push(DetailScreenVoyager(recipe.id))
                                },
                                onEditClick = { navigator?.push(AddEditRecipeScreenVoyager(recipe)) },
                                onDeleteClick = { viewModel.onDeleteRecipe(recipe.id) }
                            )
                        }

                        if (state.isLoadingMore) {
                            item {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(16.dp)
                                        .align(Alignment.CenterHorizontally)
                                )

                            }
                        }
                    }

                    if (showBottomSheet) {
                        SortBottomSheet(
                            selectedSort = state.selectedSort,
                            onSelectSort = { viewModel.onSortSelected(it) },
                            onReset = { viewModel.resetList() },
                            onDismiss = { showBottomSheet = false }
                        )
                    }

                }
            }
        }

    }

}