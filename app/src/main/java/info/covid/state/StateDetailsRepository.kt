package info.covid.state

import info.covid.customview.DataPoint
import info.covid.models.DistrictState
import info.covid.models.StateDailyResponse
import info.covid.network.RetrofitClient
import retrofit2.Response

object StateDetailsRepository {

    private val apiService = RetrofitClient.get().create(StateAPIService::class.java)
    private lateinit var districtListResp: Response<List<DistrictState>>

    suspend fun <T> getDistrictInfo(
        stateName: String,
        success: (DistrictState?) -> T,
        error: (String) -> T
    ) {
        try {

            if (::districtListResp.isInitialized.not() || districtListResp.isSuccessful.not())
                districtListResp = apiService.getStateDistrictWise()

            if (districtListResp.isSuccessful) {
                val states = districtListResp.body() ?: emptyList()
                val state = states.find { it.state == stateName }
                success(state)
            } else error("Oops, Something went wrong")
        } catch (e: Exception) {
            error("Oops, Something went wrong.")
        }
    }

    private lateinit var StateDaily: Response<StateDailyResponse>

    suspend fun <T> getStateDaily(
        state: String,
        success: (HashMap<String, ArrayList<DataPoint>>, max: Float) -> T,
        error: (String) -> T
    ) {
        try {
            if (::districtListResp.isInitialized.not() || districtListResp.isSuccessful.not())
                StateDaily = apiService.getStateDaily()

            if (StateDaily.isSuccessful) {
                val states = StateDaily.body()?.dailyStats ?: emptyList()
                val mapData = HashMap<String, ArrayList<DataPoint>>()
                var max = 0f

                states.groupBy { it.status }.forEach {
                    mapData[it.key ?: ""] = arrayListOf()
                    it.value.forEach { stateDailyItem ->
                        val temp = stateDailyItem.getCount(state).toFloat()
                        max = if (max > temp) max else temp
                        mapData[it.key]?.add(DataPoint(temp))
                    }
                }

                var confirmed = 0f
                var recovered = 0f
                var deceased = 0f
                mapData["Active"] = arrayListOf<DataPoint>().apply {
                    mapData["Confirmed"]?.forEachIndexed { index, dataPoint ->
                        recovered += mapData["Recovered"]?.get(index)?.amount ?: 0f
                        deceased += mapData["Deceased"]?.get(index)?.amount ?: 0f
                        confirmed += dataPoint.amount
                        add(DataPoint(confirmed.minus((recovered.plus(deceased)))))
                    }
                }

                success(mapData, max)
            } else error("Oops, Something went wrong")
        } catch (e: Exception) {
            error("Oops, Something went wrong.")
        }
    }
}