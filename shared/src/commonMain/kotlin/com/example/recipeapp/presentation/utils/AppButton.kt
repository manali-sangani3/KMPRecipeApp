import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppButton(onTap: () -> Unit, btnText: String) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onTap,
    ) {
        Text(btnText)
    }
}