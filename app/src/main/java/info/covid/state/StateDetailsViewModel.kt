package info.covid.state

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import info.covid.customview.DataPoint
import info.covid.database.CovidDb
import info.covid.database.enities.State
import info.covid.models.District
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StateDetailsViewModel(application: Application) : AndroidViewModel(application) {
    val districts = MutableLiveData<List<District>>()
    val stateName = MutableLiveData<String>()
    val recoversMapData = MutableLiveData<List<DataPoint>>()
    val confirmedMapData = MutableLiveData<List<DataPoint>>()
    val deceasedMapData = MutableLiveData<List<DataPoint>>()
    val activeMapData = MutableLiveData<List<DataPoint>>()
    val progress = ObservableField(View.GONE)

    private val stateDao by lazy {
        CovidDb.get(application).getCovidDao()
    }

    val state = Transformations.switchMap(stateName, ::getState)

    private fun getState(state: String): LiveData<State> {
        getDistrictsData(state)
        getDailyData(state)
        return stateDao.getState(state)
    }

    private fun getDistrictsData(state: String) {
        progress.set(View.VISIBLE)
        viewModelScope.launch(Dispatchers.IO) {
            StateDetailsRepository.getDistrictInfo(state, {
                districts.postValue((it?.districts?.sortedByDescending { it.confirmed }
                    ?: emptyList()))
                progress.set(View.GONE)
            }, {
                progress.set(View.GONE)
            })
        }
    }

    var maxNumber = 0f

    private fun getDailyData(stateName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            StateDetailsRepository.getStateDaily(stateName, { it, max ->
                maxNumber = max
                recoversMapData.postValue(it["Recovered"])
                confirmedMapData.postValue(it["Confirmed"])
                deceasedMapData.postValue(it["Deceased"])
                activeMapData.postValue(it["Active"])
            }, {

            })
        }
    }
}