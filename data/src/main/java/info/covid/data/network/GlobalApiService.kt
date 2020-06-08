package info.covid.data.network

import info.covid.data.models.global.Country
import retrofit2.Response
import retrofit2.http.GET

interface GlobalApiService {
    @GET("countries?sort=cases")
    suspend fun getAllCountryData(): Response<List<Country>>
}