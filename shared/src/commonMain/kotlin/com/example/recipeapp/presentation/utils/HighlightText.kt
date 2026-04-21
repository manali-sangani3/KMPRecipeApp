import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun highlightText(
    text: String,
    query: String
): AnnotatedString {

    if (query.isEmpty()) return AnnotatedString(text)

    val startIndex = text.lowercase().indexOf(query.lowercase())

    if (startIndex == -1) return AnnotatedString(text)

    val endIndex = startIndex + query.length

    return buildAnnotatedString {
        append(text)

        addStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
            ),
            start = startIndex,
            end = endIndex
        )
    }
}