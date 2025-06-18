package mixedwash.superapp.features.orders.data.mapper

import mixedwash.superapp.features.orders.data.entity.OrderDashboardTableItemEntity

object OrderDashboardMapper {
    fun mapToTableData(orders: List<OrderDashboardTableItemEntity>): List<List<String>> {
        val headers = listOf(
            "Order ID",
            "Customer Name",
            "Service",
            "Address",
            "Pickup Date",
            "Pickup Time",
            "Delivery Date",
            "Delivery Time",
            "Coordinates"
        )

        val rows = orders.map { order ->
            listOf(
                order.orderId,
                order.userName,
                order.service,
                order.address,
                order.pickupDateTime.pickupDate,
                order.pickupDateTime.pickupTime,
                order.deliveryDateTime.pickupDate,
                order.deliveryDateTime.pickupTime,
                order.coordinates
            )
        }

        return listOf(headers) + rows
    }
}