package info.covid.data.network

import info.covid.data.models.CovidResponse
import info.covid.data.models.Notification
import retrofit2.Response
import retrofit2.http.GET

interface CovidApiService {
    @GET("/data.json")
    suspend fun getData(): Response<CovidResponse>

    @GET("/updatelog/log.json")
    suspend fun getUpdates(): Response<List<Notification>>
}