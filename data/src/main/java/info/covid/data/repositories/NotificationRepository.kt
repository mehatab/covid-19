package info.covid.data.repositories

import info.covid.data.network.CovidApiService
import info.covid.data.utils.safeApiCall

class NotificationRepository(private val apiService: CovidApiService) {
    suspend fun getNotifications() = safeApiCall(
        fetchFromRemote = { apiService.getUpdates() },
        errorMessage = "Error getting Notification data"
    )
}