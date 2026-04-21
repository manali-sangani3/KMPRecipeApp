import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun ErrorText(text: String) {
    Text(
        text = text,
        color = Color.Red,
        style = MaterialTheme.typography.bodyMedium
    )
}