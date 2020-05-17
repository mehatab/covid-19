package info.covid.data.repositories

import info.covid.data.enities.Resources
import info.covid.data.network.EssentialsApiService
import info.covid.data.network.RetrofitClient


class EssentialsRepository {
    private val apiService = RetrofitClient.get().create(EssentialsApiService::class.java)

    suspend fun <T> getResources(success: (List<Resources>?) -> T, error: (String) -> T) {
        try {
            val resp = apiService.getResources()
            if (resp.isSuccessful) {
                success(resp.body()?.resources ?: emptyList())
            } else error("Oops, Something went wrong")
        } catch (e: Exception) {
            error("Oops, Something went wrong.")
        }
    }
}