package info.covid.home

import info.covid.models.CovidResponse
import retrofit2.Response
import retrofit2.http.GET

interface CovidApiService {
    @GET("/data.json")
    suspend fun getData() : Response<CovidResponse>
}