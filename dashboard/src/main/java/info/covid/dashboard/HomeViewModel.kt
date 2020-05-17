package info.covid.dashboard

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.Entry
import info.covid.data.enities.CovidDayInfo
import info.covid.data.enities.State
import info.covid.data.repositories.HomeRepository
import info.covid.data.utils.removeFirst
import info.covid.data.utils.toNumber
import info.covid.uicomponents.toMilliseconds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var repository = HomeRepository(application)

    val refreshing = MutableLiveData(false)
    val error = MutableLiveData<String>()

    val todayDate by lazy {
        SimpleDateFormat("dd MMMM ", Locale.getDefault()).format(Date())
    }

    val allTime = MutableLiveData(false)
    val dayList = Transformations.switchMap(allTime) {
        return@switchMap repository.getInfo()
    }

    val confirmed = ObservableField(0)
    val deaths = ObservableField(0)
    val recovered = ObservableField(0)
    val active = ObservableField(0)


    var confirmedList: ArrayList<Entry> = ArrayList()
    var deceasedList: ArrayList<Entry> = ArrayList()
    var recoveredList: ArrayList<Entry> = ArrayList()


    var dailyConfirmedList: ArrayList<Entry> = ArrayList()
    var dailyDeceasedList: ArrayList<Entry> = ArrayList()
    var dailyRecoveredList: ArrayList<Entry> = ArrayList()

    val stateDataList = Transformations.map(repository.getStatesWithTotal()) {
        if (!it.isNullOrEmpty()) {
            it.first().also { total ->
                today.set(total)
                confirmed.set(total.confirmed.toNumber())
                deaths.set(total.deaths.toNumber())
                active.set(total.active.toNumber())
                recovered.set(total.recovered.toNumber())
            }

            return@map it.removeFirst()
        } else return@map it
    }

    val today = ObservableField<State>()

    val chartData = Transformations.map(dayList) { resp ->
        confirmedList.clear()
        deceasedList.clear()
        recoveredList.clear()
        dailyConfirmedList.clear()
        dailyDeceasedList.clear()
        dailyRecoveredList.clear()


        return@map arrayListOf<CovidDayInfo>().apply {
            resp.forEachIndexed { index, it ->
                if (((resp.size - 30) < index) || allTime.value == true) {
                    val time = it.date?.toMilliseconds() ?: 0f
                    confirmedList.add(
                        Entry(
                            time,
                            it.totalconfirmed.toNumber().toFloat()
                        )
                    )

                    deceasedList.add(
                        Entry(
                            time,
                            it.totaldeceased.toNumber().toFloat()
                        )
                    )

                    recoveredList.add(
                        Entry(
                            time,
                            it.totalrecovered.toNumber().toFloat()
                        )
                    )

                    dailyConfirmedList.add(
                        Entry(
                            time,
                            it.dailyconfirmed.toNumber().toFloat()
                        )
                    )

                    dailyDeceasedList.add(
                        Entry(
                            time,
                            it.dailydeceased.toNumber().toFloat()
                        )
                    )

                    dailyRecoveredList.add(
                        Entry(
                            time,
                            it.dailyrecovered.toNumber().toFloat()
                        )
                    )
                }
            }
        }
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
                                repository.insert(it.result ?: emptyList())
                                repository.insert(today)
                                if (!it.key_values.isNullOrEmpty()) {
                                    repository.insert(it.key_values!![0].apply {
                                        TodayID = 1
                                    })
                                }

                                repository.insertStateWise(it.statewise ?: emptyList())
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

