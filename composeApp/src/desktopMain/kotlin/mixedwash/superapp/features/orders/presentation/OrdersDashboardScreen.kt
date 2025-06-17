package mixedwash.superapp.features.orders.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OrdersDashboardScreen(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .padding(vertical = 28.dp, horizontal = 24.dp)
    ) {
        Text(
            text = "Order Dashboard",
            color = Color(0xffE0E0E0),
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
        )
    }
}

