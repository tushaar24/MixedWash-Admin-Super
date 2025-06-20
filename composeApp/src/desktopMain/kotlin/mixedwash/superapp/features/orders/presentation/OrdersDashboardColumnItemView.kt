package mixedwash.superapp.features.orders.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun OrdersDashboardColumnItemView(
    label: String,
    isTitle: Boolean = false,
    modifier: Modifier = Modifier
){
    SelectionContainer {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = if (isTitle) FontWeight.Bold else FontWeight.Normal,
            textAlign = TextAlign.Center,
            color = Color(0xffE0E0E0),
            modifier = modifier,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}
