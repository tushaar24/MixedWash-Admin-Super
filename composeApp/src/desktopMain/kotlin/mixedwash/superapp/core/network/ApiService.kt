package mixedwash.superapp.core.network

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

class ApiService(
    private val baseUrl: String = "https://api.example.com"
) {
    private val client = KtorClient.httpClient

    suspend fun getExample(): ExampleResponse {
        return client.get("$baseUrl/example").body()
    }

    suspend fun postExample(request: ExampleRequest): ExampleResponse {
        return client.post("$baseUrl/example") {
            setBody(request)
        }.body()
    }

    suspend fun updateExample(id: String, request: ExampleRequest): ExampleResponse {
        return client.put("$baseUrl/example/$id") {
            setBody(request)
        }.body()
    }

    suspend fun deleteExample(id: String): HttpResponse {
        return client.delete("$baseUrl/example/$id")
    }

    fun close() {
        client.close()
    }
}

@Serializable
data class ExampleRequest(
    val name: String,
    val description: String
)

@Serializable
data class ExampleResponse(
    val id: String,
    val name: String,
    val description: String,
    val createdAt: String
)