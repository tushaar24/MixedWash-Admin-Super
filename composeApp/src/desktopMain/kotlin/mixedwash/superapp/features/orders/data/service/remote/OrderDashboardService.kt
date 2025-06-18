package mixedwash.superapp.features.orders.data.service.remote

import io.ktor.client.call.body
import io.ktor.client.request.get
import mixedwash.superapp.core.network.KtorClient
import mixedwash.superapp.features.orders.data.entity.OrderDashboardTableItemEntity

class OrderDashboardService() {
    val basUrl = "https://mixedwash-super.onrender.com"
    val orders = "$basUrl/orders"

    suspend fun getOrders(): List<OrderDashboardTableItemEntity> {
        return KtorClient.httpClient.get(orders).body()
    }
}