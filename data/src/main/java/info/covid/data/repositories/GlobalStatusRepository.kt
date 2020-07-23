package info.covid.data.repositories

import android.util.Log
import info.covid.data.dao.GlobalDao
import info.covid.data.models.Result
import info.covid.data.network.GlobalApiService
import info.covid.data.utils.safeApiCall

class GlobalStatusRepository(
    private val apiService: GlobalApiService,
    private val globalDao: GlobalDao
) {

    suspend fun getAllCountryData() = safeApiCall(
        fetchFromRemote = {
            apiService.getAllCountryData()
        },
        saveRemoteData = {
            globalDao.deleteAll()
            globalDao.insert(it)
        },
        fetchFromLocal = { Result.Success(globalDao.getAllCountryData()) },
        errorMessage = "Error getting data"
    )

    fun getCountry(countryName : String) = globalDao.getCountry(countryName)

    suspend fun getAllCountry(query : String) = globalDao.getAllCountryData(query)
}