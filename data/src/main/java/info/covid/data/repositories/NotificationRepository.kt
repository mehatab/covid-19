package info.covid.data.repositories

import info.covid.data.models.Notification
import info.covid.data.network.CovidApiService

class NotificationRepository(private val apiService: CovidApiService) {

    suspend fun <T> getUpdates(success: (List<Notification>) -> T, error: (String) -> T) {
        try {
            val resp = apiService.getUpdates()
            if (resp.isSuccessful) {
                success(resp.body() ?: emptyList())
            } else error("Oops, Something went wrong")
        } catch (e: Exception) {
            error("Oops, Something went wrong.")
        }
    }
}