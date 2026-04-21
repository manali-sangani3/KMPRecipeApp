import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortBottomSheet(
    selectedSort: SortType?,
    onSelectSort: (SortType) -> Unit,
    onReset: () -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        containerColor = Color.White,
        onDismissRequest = onDismiss
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            // 🔹 Header
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Sort By",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Reset",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.clickable {
                        onReset()
                        onDismiss()
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Option 1
            SortItem(
                title = "Name (A → Z)",
                isSelected = selectedSort == SortType.NAME_ASC,
                onClick = {
                    onSelectSort(SortType.NAME_ASC)
                    onDismiss()
                }
            )

            // 🔹 Option 2
            SortItem(
                title = "Name (Z → A)",
                isSelected = selectedSort == SortType.NAME_DESC,
                onClick = {
                    onSelectSort(SortType.NAME_DESC)
                    onDismiss()
                }
            )
        }
    }
}
@Composable
fun SortItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Text(
        text = title,
        style = if (isSelected) {
            TextStyle(fontWeight = FontWeight.Bold)
        } else {
            TextStyle()
        },
        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.09f)
                else Color.Transparent,
                RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(12.dp)
    )
}