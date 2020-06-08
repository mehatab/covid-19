package info.covid.data.repositories

import info.covid.data.dao.CovidDao
import info.covid.data.enities.CovidDayInfo
import info.covid.data.enities.KeyValues
import info.covid.data.enities.State
import info.covid.data.models.CovidResponse
import info.covid.data.network.CovidApiService
import java.text.SimpleDateFormat
import java.util.*

class HomeRepository(
    val dao: CovidDao,
    private val apiService: CovidApiService
) {

    suspend fun <T> getData(success: (CovidResponse?) -> T, error: (String) -> T) {
        try {
            val resp = apiService.getData()
            if (resp.isSuccessful) {
                success(resp.body())
                updateLocalData(resp.body())
            } else error("Oops, Something went wrong")
        } catch (e: Exception) {
            error("Oops, Something went wrong.")
        }
    }

    private val todayDate by lazy {
        SimpleDateFormat("dd MMMM ", Locale.getDefault()).format(Date())
    }

    private suspend fun updateLocalData(resp: CovidResponse?) {
        resp?.let {

            if (!resp.result.isNullOrEmpty()) {
                val today = CovidDayInfo()

                today.apply {

                    it.statewise?.first()?.let { it ->
                        dailyconfirmed = it.deltaconfirmed
                        dailyrecovered = it.deltarecovered
                        dailydeceased = it.deltadeaths
                        totalconfirmed = it.confirmed
                        totaldeceased = it.deaths
                        totalrecovered = it.recovered
                    }

                    date = todayDate
                }

                insert(it.result ?: emptyList())
                insert(today)
                if (!it.key_values.isNullOrEmpty()) {
                    insert(it.key_values!![0].apply {
                        TodayID = 1
                    })
                }

                insertStateWise(it.statewise ?: emptyList())
            }
        }
    }

    fun getInfo() = dao.getInfo()

    fun getStatesWithTotal() = dao.getStatesWithTotal()

    private suspend fun insert(list: List<CovidDayInfo>) {
        dao.insert(list)
    }

    private suspend fun insert(today: CovidDayInfo) {
        dao.insert(today)
    }

    private suspend fun insert(keyValues: KeyValues) {
        dao.insert(keyValues)
    }

    private suspend fun insertStateWise(list: List<State>) {
        dao.insertStateWise(list)
    }
}