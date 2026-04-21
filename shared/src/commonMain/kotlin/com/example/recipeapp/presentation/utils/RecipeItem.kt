import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun RecipeItem(
    recipe: Recipe, searchQuery: String, onClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier.padding(12.dp)
            .fillMaxWidth().clickable { onClick() }
            .shadow(10.dp, RoundedCornerShape(24.dp), ambientColor = Color.Gray.copy(alpha = 0.2f)),
        shape = RoundedCornerShape(24.dp),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.onSecondaryContainer,
            contentColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified,
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(end = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            KamelImage(
                resource = asyncPainterResource(recipe.image),
                contentDescription = null,
                alignment = Alignment.Center,
                onFailure = {},
                modifier = Modifier.size(170.dp)
                    .clip(RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp))
            )

            Spacer(modifier = Modifier.height(6.dp))

            Column(
                horizontalAlignment = Alignment.Start,
//                modifier = Modifier.padding(vertical = 24.dp),
            ) {

                Text(
                    text = highlightText(recipe.name, searchQuery),
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row {
                    Icon(
                        Icons.Default.AccessTime,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "${recipe.prepTimeMinutes} mins",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Serves ${recipe.servings}",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row {
                    IconBg(
                        content = "Edit",
                        icon = Icons.Default.Edit,
                        size = 32.dp,
                        onClick = { onEditClick() })
                    Spacer(modifier = Modifier.width(12.dp))
                    IconBg(
                        content = "Delete",
                        icon = Icons.Default.Delete,
                        size = 32.dp,
                        onClick = { onDeleteClick() })
                }
            }
        }
    }
}