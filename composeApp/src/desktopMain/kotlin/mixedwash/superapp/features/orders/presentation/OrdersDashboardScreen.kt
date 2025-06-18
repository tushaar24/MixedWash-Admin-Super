package mixedwash.superapp.features.orders.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
    val transposed = listOf(
        listOf("ID", "Source", "Customer Name", "Pickup Date-Time", "Delivery Date-Time", "Locality", "Status", "Pickup Driver", "Delivery Driver", "Amount"),
        listOf("a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2", "i2", "j2"),
        listOf("a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3", "i3", "j3"),
        listOf("a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4", "i4", "j4"),
        listOf("a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5", "i5", "j5"),
        listOf("a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6", "i6", "j6"),
        listOf("a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7", "i7", "j7"),
        listOf("a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8", "i8", "j8"),
        listOf("a9", "b9", "c9", "d9", "e9", "f9", "g9", "h9", "i9", "j9"),
        listOf("a10", "b10", "c10", "d10", "e10", "f10", "g10", "h10", "i10", "j1asdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfadsf0")
    )
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

        OrdersDashboardTableView(
            transposed,
            modifier = Modifier.padding(top = 16.dp).fillMaxWidth()
        )
    }
}

