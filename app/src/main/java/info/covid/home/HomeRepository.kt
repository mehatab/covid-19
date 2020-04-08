package info.covid.home

import info.covid.models.CovidResponse
import info.covid.network.RetrofitClient

class HomeRepository {

    private val apiService = RetrofitClient.get().create(CovidApiService::class.java)

    suspend fun <T> getData(success: (CovidResponse?) -> T, error: (String) -> T) {
        try {
            val resp = apiService.getData()
            if (resp.isSuccessful){
                success(resp.body())
            } else error("Oops, Something went wrong")
        } catch (e: Exception) {
            error("Oops, Something went wrong.")
        }
    }
}