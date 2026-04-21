import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp

@Composable
fun IconBg(icon: ImageVector, content: String, size: Dp, onClick: () -> Unit) {
    Box(
        modifier = Modifier.size(size).background(
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), shape = CircleShape
        ).clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = content,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}