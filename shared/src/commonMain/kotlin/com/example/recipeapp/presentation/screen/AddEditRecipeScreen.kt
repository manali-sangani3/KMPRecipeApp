import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator

class AddEditRecipeScreenVoyager(private val recipe: Recipe? = null) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel =
            remember { AddRecipeViewModel(AppModule.addRecipeUseCase, AppModule.editRecipeUseCase) }
        val state by viewModel.state.collectAsState()
        val snackbarHostState = remember { SnackbarHostState() }
        val isEdit = recipe != null
        Scaffold(
            contentWindowInsets = WindowInsets.safeDrawing,
            containerColor = Color.White,
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .statusBarsPadding()
                        .padding(start = 24.dp, end = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconBg(
                        content = "Cancel",
                        icon = Icons.Default.Close,
                        size = 42.dp,
                        onClick = { navigator?.pop() })
                    Text(
                        text = if (isEdit) "Edit Recipe" else "Add Recipe",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium,
                            fontSize = 24.sp
                        )
                    )
                    IconBg(
                        content = "Save",
                        icon = Icons.Default.Check,
                        size = 42.dp,
                        onClick = {
                            if (isEdit) {
                                viewModel.updateRecipe(recipe!!.id)
                            } else {
                                viewModel.saveRecipe()
                            }
                        })
                }
            }) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp).padding(paddingValues)
            ) {

                Spacer(Modifier.height(16.dp))
                LaunchedEffect(state.isSuccess) {
                    if (state.isSuccess) {

                        snackbarHostState.showSnackbar(
                            message = if (isEdit)
                                "Recipe updated successfully"
                            else
                                "Recipe added successfully"
                        )

                        navigator?.pop()
                    }
                }
                LaunchedEffect(Unit) {
                    if (isEdit) {
                        viewModel.setRecipeForEdit(recipe!!)
                    }
                }
                if (state.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }
                state.error?.let {
                    LaunchedEffect(it) {
                        snackbarHostState.showSnackbar(it)
                    }
                }
                Label("Name")
                AppTextField(
                    hint = "Enter Food Name",
                    value = state.name,
                    keyboardType = KeyboardType.Text,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { viewModel.onNameChange(it) }
                )


                Spacer(Modifier.height(16.dp))

                Label("Ingredients")
                state.ingredients.forEachIndexed { index, item ->
                    val isLast = index == state.ingredients.lastIndex

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        AppTextField(
                            value = item,
                            onValueChange = {
                                viewModel.onIngredientChange(index, it)
                            },
                            hint = "Add ingredient",
                            keyboardType = KeyboardType.Text,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(Modifier.width(12.dp))
                        IconBg(
                            icon = if (isLast) Icons.Default.Add else Icons.Default.Remove,
                            content = "",
                            size = 20.dp,
                            onClick = {
                                if (isLast) {
                                    viewModel.addIngredient()
                                } else {
                                    viewModel.removeIngredient(index)
                                }
                            }
                        )
                    }

                    Spacer(Modifier.height(8.dp))
                }

                Label("Instructions")
                state.instructions.forEachIndexed { index, item ->
                    val isLast = index == state.instructions.lastIndex
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        AppTextField(
                            value = item,
                            onValueChange = {
                                viewModel.onInstructionChange(index, it)
                            },
                            hint = "Add step",
                            keyboardType = KeyboardType.Text,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(Modifier.width(12.dp))
                        IconBg(
                            icon = if (isLast) Icons.Default.Add else Icons.Default.Remove,
                            content = "",
                            size = 20.dp,
                            onClick = {
                                if (isLast) {
                                    viewModel.addInstruction()
                                } else {
                                    viewModel.removeInstruction(index)
                                }
                            }
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                }


                Spacer(Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        Label("Cook Time")

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            AppTextField(
                                value = state.cookTimeHour,
                                onValueChange = { viewModel.onCookTimeHourChange(it) },
                                hint = "h",
                                keyboardType = KeyboardType.Number,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                ":",
                                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Medium)
                            )
                            AppTextField(
                                value = state.cookTimeMin,
                                onValueChange = { viewModel.onCookTimeMinChange(it) },
                                hint = "min",
                                keyboardType = KeyboardType.Number,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        Label("Preparation Time")

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            AppTextField(
                                value = state.prepTimeHour,
                                onValueChange = { viewModel.onPrepTimeHourChange(it) },
                                hint = "h",
                                keyboardType = KeyboardType.Number,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                ":",
                                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Medium)
                            )
                            AppTextField(
                                value = state.prepTimeMin,
                                onValueChange = { viewModel.onPrepTimeMinChange(it) },
                                hint = "min",
                                keyboardType = KeyboardType.Number,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }


                Spacer(Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        Label("Servings")

                        AppTextField(
                            value = state.servings,
                            onValueChange = { viewModel.addServings(it) },
                            hint = "Enter Servings",
                            keyboardType = KeyboardType.Number,
                            modifier = Modifier
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Label("Calories per person")

                        AppTextField(
                            value = state.caloriesPerServing,
                            onValueChange = { viewModel.addCalories(it) },
                            hint = "Enter Calories",
                            keyboardType = KeyboardType.Number,
                            modifier = Modifier
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))
            }
        }

    }
}