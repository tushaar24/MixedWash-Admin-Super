package mixedwash.superapp.features.orders.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.launch
import mixedwash.superapp.features.orders.data.mapper.OrderDashboardMapper
import mixedwash.superapp.features.orders.data.service.remote.OrderDashboardService

@Composable
fun OrdersDashboardScreen(
    modifier: Modifier = Modifier
){
    var tableData by remember { mutableStateOf<List<List<String>>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val orderService = remember { OrderDashboardService() }

    LaunchedEffect(Unit) {
        launch {
            try {
                isLoading = true
                errorMessage = null
                val orders = orderService.getOrders()
                tableData = OrderDashboardMapper.mapToTableData(orders)
            } catch (e: Exception) {
                errorMessage = "Failed to load orders: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

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

        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            errorMessage != null -> {
                Text(
                    text = errorMessage!!,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            else -> {
                OrdersDashboardTableView(
                    tableData,
                    modifier = Modifier.padding(top = 16.dp).fillMaxWidth()
                )
            }
        }
    }
}
