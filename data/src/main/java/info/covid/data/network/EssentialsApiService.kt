package info.covid.data.network

import info.covid.data.models.EssentialsResponse
import retrofit2.Response
import retrofit2.http.GET

interface EssentialsApiService {

    @GET("/resources/resources.json")
    suspend fun getResources(): Response<EssentialsResponse>
}