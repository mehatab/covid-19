package info.covid.data.repositories

import info.covid.data.dao.CovidDao
import info.covid.data.enities.CovidDayInfo
import info.covid.data.enities.KeyValues
import info.covid.data.enities.State
import info.covid.data.models.CovidResponse
import info.covid.data.network.CovidApiService
import info.covid.data.network.RetrofitClient

class HomeRepository(val dao : CovidDao) {

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

    fun getInfo() = dao.getInfo()
    fun getStatesWithTotal() = dao.getStatesWithTotal()
    suspend fun insert(list: List<CovidDayInfo>) {
        dao.insert(list)
    }
    suspend fun insert(today: CovidDayInfo) {
        dao.insert(today)
    }
    suspend fun insert(keyValues: KeyValues) {
        dao.insert(keyValues)
    }

    suspend fun insertStateWise(list: List<State>) {
        dao.insertStateWise(list)
    }
}