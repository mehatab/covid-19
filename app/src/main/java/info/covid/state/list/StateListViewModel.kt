package info.covid.state.list

import android.app.Application
import androidx.lifecycle.*
import info.covid.customview.DataPoint
import info.covid.database.CovidDb
import info.covid.database.enities.CovidDayInfo
import info.covid.home.HomeRepository
import info.covid.utils.toNumber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class StateListViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = CovidDb.get(application).getCovidDao()

    val states = dao.getStates()

    private var repository = HomeRepository()
    val refreshing = MutableLiveData(false)
    val error = MutableLiveData<String>()

    val todayDate by lazy {
        SimpleDateFormat("dd MMMM ", Locale.getDefault()).format(Date())
    }

    val total = dao.getTotal()

    var maxValue = 0f

    val dayList: LiveData<Map<String, ArrayList<DataPoint>>> = Transformations.map(dao.getInfo()) {
        val mapData = HashMap<String, ArrayList<DataPoint>>()
        maxValue = it.maxBy { it.totalconfirmed.toNumber() }?.totalconfirmed.toNumber().toFloat()

        mapData["Confirmed"] = it.map { item ->
            DataPoint(
                item.totalconfirmed.toNumber().toFloat()
            )
        } as ArrayList<DataPoint>

        mapData["Deceased"] = it.map { item ->
            DataPoint(
                item.totaldeceased.toNumber().toFloat()
            )
        } as ArrayList<DataPoint>

        mapData["Recovered"] = it.map { item ->
            DataPoint(
                item.totalrecovered.toNumber().toFloat()
            )
        } as ArrayList<DataPoint>

        mapData["Active"] = arrayListOf()

        mapData["Confirmed"]?.forEachIndexed { index, dataPoint ->
            mapData["Active"]?.add(
                DataPoint(
                    dataPoint.amount.minus(
                        mapData["Deceased"]?.get(index)?.amount?.plus(
                            mapData["Recovered"]?.get(index)?.amount ?: 0f
                        ) ?: 0f
                    )
                )
            )
        }

        return@map mapData
    }


    init {
        getDate()
    }


    fun getDate() {
        viewModelScope.launch(Dispatchers.IO) {
            refreshing.postValue(true)
            repository.getData({ resp ->

                viewModelScope.launch(Dispatchers.IO) {
                    resp?.let {
                        if (!resp.result.isNullOrEmpty()) {
                            val today = CovidDayInfo()

                            today.apply {
                                val total = it.statewise?.first()

                                dailyconfirmed = total?.deltaconfirmed
                                dailyrecovered = total?.deltarecovered
                                dailydeceased = total?.deltadeaths
                                totalconfirmed = total?.confirmed
                                totaldeceased = total?.deaths
                                totalrecovered = total?.recovered

                                date = todayDate
                            }


                            viewModelScope.launch(Dispatchers.IO) {
                                dao.insert(it.result ?: emptyList())
                                dao.insert(today)
                                if (!it.key_values.isNullOrEmpty()) {
                                    dao.insert(it.key_values!![0].apply {
                                        TodayID = 1
                                    })
                                }

                                dao.insertStateWise(it.statewise ?: emptyList())
                            }
                        }
                    }
                }

                refreshing.postValue(false)
            }, {
                refreshing.postValue(false)
                error.postValue(it)
            })
        }
    }
}