package info.covid.data.repositories

import info.covid.data.models.Notification
import info.covid.data.network.CovidApiService
import info.covid.data.network.RetrofitClient


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