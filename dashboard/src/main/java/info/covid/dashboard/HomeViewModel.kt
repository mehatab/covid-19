package info.covid.dashboard

import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieEntry
import info.covid.data.enities.CovidDayInfo
import info.covid.data.enities.State
import info.covid.data.repositories.HomeRepository
import info.covid.data.utils.percentage
import info.covid.data.utils.removeFirst
import info.covid.data.utils.toNumber
import info.covid.data.utils.top
import info.covid.uicomponents.toMilliseconds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    val refreshing = MutableLiveData(false)
    val error = MutableLiveData<String>()

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

    val pieEntries = ArrayList<PieEntry>()

    val stateDataList: LiveData<List<PieEntry>> =
        Transformations.map(repository.getStatesWithTotal()) {
            pieEntries.clear()
            if (!it.isNullOrEmpty()) {
                it.first().also { total ->
                    today.set(total)
                    confirmed.set(total.confirmed.toNumber())
                    deaths.set(total.deaths.toNumber())
                    active.set(total.active.toNumber())
                    recovered.set(total.recovered.toNumber())
                }


                it.removeFirst().top(10).map {
                    pieEntries.add(PieEntry(confirmed.get().percentage(it.confirmed), it.state))
                }

                pieEntries.add(PieEntry(100f - pieEntries.sumByDouble { it.value.toDouble() }.toFloat(), "Other"))
            }

            return@map pieEntries
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
                    confirmedList.add(Entry(time, it.totalconfirmed.toNumber().toFloat()))

                    deceasedList.add(Entry(time, it.totaldeceased.toNumber().toFloat()))

                    recoveredList.add(Entry(time, it.totalrecovered.toNumber().toFloat()))

                    dailyConfirmedList.add(Entry(time, it.dailyconfirmed.toNumber().toFloat()))

                    dailyDeceasedList.add(Entry(time, it.dailydeceased.toNumber().toFloat()))

                    dailyRecoveredList.add(Entry(time, it.dailyrecovered.toNumber().toFloat()))
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
            repository.getData({
                refreshing.postValue(false)
            }, {
                refreshing.postValue(false)
                error.postValue(it)
            })
        }
    }
}

