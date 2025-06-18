package mixedwash.superapp.features.orders.data.mapper

import mixedwash.superapp.features.orders.data.entity.OrderDashboardTableItemEntity
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object OrderDashboardMapper {
    fun mapToTableData(orders: List<OrderDashboardTableItemEntity>): List<List<String>> {
        val headers = listOf(
            "Order ID",
            "Customer Name",
            "Phone Number",
            "Email Address",
            "Service",
            "Address",
            "Pickup Date",
            "Pickup Time",
            "Delivery Date",
            "Delivery Time",
            "Coordinates"
        )

        val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val sortedOrders = orders.sortedByDescending { order ->
            try {
                LocalDate.parse(order.pickupDateTime.pickupDate, dateFormatter)
            } catch (e: Exception) {
                // Fallback to string comparison if parsing fails
                LocalDate.MIN
            }
        }

        val rows = sortedOrders.map { order ->
            listOf(
                order.orderId,
                order.userName,
                order.userPhone,
                order.emailAddress,
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

    private fun fuzzyMatch(text: String, query: String): Boolean {
        if (query.isBlank()) return true

        val normalizedText = text.lowercase().replace(Regex("[^a-z0-9]"), "")
        val normalizedQuery = query.lowercase().replace(Regex("[^a-z0-9]"), "")

        // Exact match
        if (normalizedText.contains(normalizedQuery)) return true

        // Subsequence match - check if all characters of query appear in order in text
        var textIndex = 0
        var queryIndex = 0

        while (textIndex < normalizedText.length && queryIndex < normalizedQuery.length) {
            if (normalizedText[textIndex] == normalizedQuery[queryIndex]) {
                queryIndex++
            }
            textIndex++
        }

        // If we've matched all characters in the query
        if (queryIndex == normalizedQuery.length) return true

        // Word-based matching - split by spaces and check if any word starts with query
        val words = text.lowercase().split(Regex("\\s+"))
        val queryWords = query.lowercase().split(Regex("\\s+"))

        return queryWords.all { queryWord ->
            words.any { word -> word.startsWith(queryWord) }
        }
    }

    fun mapToFilteredTableData(
        orders: List<OrderDashboardTableItemEntity>,
        fromDate: String? = null,
        toDate: String? = null,
        customerName: String? = null,
        phoneNumber: String? = null,
        emailAddress: String? = null,
        service: String? = null
    ): List<List<String>> {
        val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        val filteredOrders = orders.filter { order ->
            var matches = true

            // Filter by date range
            if (!fromDate.isNullOrBlank() || !toDate.isNullOrBlank()) {
                try {
                    val orderDate = LocalDate.parse(order.pickupDateTime.pickupDate, dateFormatter)

                    if (!fromDate.isNullOrBlank()) {
                        val fromLocalDate = LocalDate.parse(fromDate, dateFormatter)
                        matches = matches && !orderDate.isBefore(fromLocalDate)
                    }

                    if (!toDate.isNullOrBlank()) {
                        val toLocalDate = LocalDate.parse(toDate, dateFormatter)
                        matches = matches && !orderDate.isAfter(toLocalDate)
                    }
                } catch (e: Exception) {
                    matches = false
                }
            }

            // Fuzzy search for customer name
            if (!customerName.isNullOrBlank()) {
                matches = matches && fuzzyMatch(order.userName, customerName)
            }

            // Fuzzy search for phone number
            if (!phoneNumber.isNullOrBlank()) {
                matches = matches && fuzzyMatch(order.userPhone, phoneNumber)
            }

            // Fuzzy search for email address
            if (!emailAddress.isNullOrBlank()) {
                matches = matches && fuzzyMatch(order.emailAddress, emailAddress)
            }

            // Fuzzy search for service
            if (!service.isNullOrBlank()) {
                matches = matches && fuzzyMatch(order.service, service)
            }

            matches
        }

        val headers = listOf(
            "Order ID",
            "Customer Name",
            "Phone Number",
            "Email Address",
            "Service",
            "Address",
            "Pickup Date",
            "Pickup Time",
            "Delivery Date",
            "Delivery Time",
            "Coordinates"
        )

        val sortedOrders = filteredOrders.sortedByDescending { order ->
            try {
                LocalDate.parse(order.pickupDateTime.pickupDate, dateFormatter)
            } catch (e: Exception) {
                LocalDate.MIN
            }
        }

        val rows = sortedOrders.map { order ->
            listOf(
                order.orderId,
                order.userName,
                order.userPhone,
                order.emailAddress,
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
