package info.covid.data.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object RetrofitClient {
   const val BASE_URL = "https://api.covid19india.org"

    fun get(baseUrl : String?= BASE_URL) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}