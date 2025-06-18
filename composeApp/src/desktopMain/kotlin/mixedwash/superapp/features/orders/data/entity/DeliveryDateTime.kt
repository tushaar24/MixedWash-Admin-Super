package mixedwash.superapp.features.orders.data.entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeliveryDateTime(
    @SerialName("pickup_date")
    val pickupDate: String,
    @SerialName("pickup_time")
    val pickupTime: String
)