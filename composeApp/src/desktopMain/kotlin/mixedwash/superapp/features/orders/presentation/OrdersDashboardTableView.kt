package mixedwash.superapp.features.orders.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OrdersDashboardTableView(
    columnValues: List<List<String>>,
    modifier: Modifier = Modifier,
){
    val scrollState = rememberScrollState()
    var showDetailsDialog by remember { mutableStateOf(false) }
    var selectedRowData by remember { mutableStateOf<List<String>>(emptyList()) }

    if (columnValues.isEmpty()) return

    Column(modifier = modifier) {
        // Sticky header row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(Color(0xff252525))
                .horizontalScroll(scrollState)
        ) {
            columnValues[0].forEachIndexed { yIndex, value ->
                OrdersDashboardColumnItemView(
                    label = value,
                    isTitle = true, // All header cells should be bold
                    modifier = Modifier.width(400.dp)
                )
            }
            // Add "Actions" header
            OrdersDashboardColumnItemView(
                label = "Actions",
                isTitle = true,
                modifier = Modifier.width(120.dp)
            )
        }

        // Scrollable content (data rows)
        LazyColumn(
            modifier = Modifier.horizontalScroll(scrollState)
        ) {
            items(count = columnValues.size - 1) { index ->
                val actualIndex = index + 1 // Skip header row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(
                            if (actualIndex % 2 == 0)
                                Color(0xff252525)
                            else
                                Color(0xff2A2A2A)
                        )
                ) {
                    columnValues[actualIndex].forEachIndexed { yIndex, value ->
                        OrdersDashboardColumnItemView(
                            label = value,
                            isTitle = false, // All data cells should be normal weight
                            modifier = Modifier.width(400.dp)
                        )
                    }
                    // Add "View Details" button
                    TextButton(
                        onClick = {
                            selectedRowData = columnValues[actualIndex]
                            showDetailsDialog = true
                        },
                        modifier = Modifier.width(120.dp)
                    ) {
                        Text(
                            text = "View Details",
                            color = Color(0xff4CAF50),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }

    // Order Details Dialog
    if (showDetailsDialog && selectedRowData.isNotEmpty()) {
        OrderDetailsDialog(
            orderData = selectedRowData,
            headers = columnValues[0],
            onDismiss = { showDetailsDialog = false }
        )
    }
}
