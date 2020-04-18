package info.covid.notification

import info.covid.home.CovidApiService
import info.covid.models.Notification
import info.covid.network.RetrofitClient

class NotificationRepository {

    private val apiService = RetrofitClient.get().create(CovidApiService::class.java)

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