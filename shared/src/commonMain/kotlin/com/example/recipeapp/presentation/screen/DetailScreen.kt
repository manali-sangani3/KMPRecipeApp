import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun RecipeDetailScreen(
    recipe: Recipe,
    onBack: () -> Unit = {},
) {
    Scaffold(containerColor = Color.White) { padding ->

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {

                Box(
                    modifier = Modifier.fillMaxWidth().height(400.dp).clip(
                        RoundedCornerShape(
                            bottomStart = 40.dp, bottomEnd = 40.dp
                        )
                    )
                ) {

                    KamelImage(
                        resource = asyncPainterResource(recipe.image),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )

                    Box(
                        modifier = Modifier.fillMaxSize().background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent, Color.Black.copy(alpha = 0.6f)
                                )
                            )
                        )
                    )

                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.padding(
                            top = 50.dp,
                            start = 24.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        ).size(40.dp).background(Color.White, CircleShape)
                            .align(Alignment.TopStart)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }

                    Column(
                        modifier = Modifier.align(Alignment.BottomEnd)
                            .padding(bottom = 24.dp, start = 16.dp, end = 16.dp, top = 16.dp)
                    ) {
                        Text(
                            text = recipe.name,
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.White
                        )
                        Text(
                            text = "${recipe.prepTimeMinutes} minutes + ${recipe.servings} servings (${recipe.caloriesPerServing} kcal)",
                            color = Color.White,
                        )
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth()
                        .background(Color.White).padding(16.dp)
                ) {

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = "This recipe is delicious and easy to prepare.",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(Modifier.height(16.dp))

                    ExpandableSection(
                        title = "Ingredients", items = recipe.ingredients
                    )

                    Spacer(Modifier.height(16.dp))

                    ExpandableSteps(
                        title = "Directions", steps = recipe.instructions
                    )

                }
            }
        }
    }
}

@Composable
fun ExpandableSection(
    title: String, items: List<String>
) {
    var expanded by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(modifier = Modifier.fillMaxWidth().clickable { expanded = !expanded }
            .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = title.uppercase(),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = if (expanded) "−" else "+", // 🔥 toggle
                fontSize = 22.sp, fontWeight = FontWeight.Bold
            )
        }

        if (expanded) {
            Column {
                items.forEach {
                    Text(
                        text = "• $it", modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ExpandableSteps(
    title: String, steps: List<String>
) {
    var expanded by remember { mutableStateOf(true) } // default open

    Column {

        Row(modifier = Modifier.fillMaxWidth().clickable { expanded = !expanded }
            .padding(vertical = 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = title.uppercase(),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = if (expanded) "−" else "+", fontSize = 22.sp, fontWeight = FontWeight.Bold
            )
        }

        if (expanded) {
            steps.forEachIndexed { index, step ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "${index + 1}",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(step)
                }
            }
        }
    }
}