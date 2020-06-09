package info.covid.data.network

import com.squareup.moshi.Moshi
import info.covid.data.converters.CountryCodeConverters
import info.covid.data.converters.MoshiCurrencyConverter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object RetrofitClient {
   const val BASE_URL = "https://api.covid19india.org"
   const val GLOBAL_BASE_URL = "https://corona.lmao.ninja/v2/"

    fun get(baseUrl : String?= BASE_URL) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()


    private val moshi = Moshi.Builder()
        .add(CountryCodeConverters)
        .build()
}