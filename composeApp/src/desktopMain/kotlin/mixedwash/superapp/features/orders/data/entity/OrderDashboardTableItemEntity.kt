package mixedwash.superapp.features.orders.data.entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderDashboardTableItemEntity(
    @SerialName("address")
    val address: String,
    @SerialName("coordinates")
    val coordinates: String,
    @SerialName("delivery_date_time")
    val deliveryDateTime: DeliveryDateTime,
    @SerialName("order_id")
    val orderId: String,
    @SerialName("pickup_date_time")
    val pickupDateTime: PickupDateTime,
    @SerialName("service")
    val service: String,
    @SerialName("user_name")
    val userName: String,
    @SerialName("phone_number")
    val userPhone: String,
    @SerialName("email_address")
    val emailAddress: String
)