package info.covid.state

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import info.covid.data.enities.State
import info.covid.data.models.DataPoint
import info.covid.data.models.District
import info.covid.data.repositories.StateDetailsRepository
import info.covid.data.repositories.StateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StateDetailsViewModel(
    private val repo: StateRepository,
    private val stateDetailsRepository: StateDetailsRepository
) : ViewModel() {
    val districts = MutableLiveData<List<District>>()
    val stateName = MutableLiveData<String>()
    val recoversMapData = MutableLiveData<List<DataPoint>>()
    val confirmedMapData = MutableLiveData<List<DataPoint>>()
    val deceasedMapData = MutableLiveData<List<DataPoint>>()
    val activeMapData = MutableLiveData<List<DataPoint>>()
    val progress = ObservableField(View.GONE)

    val state = Transformations.switchMap(stateName, ::getState)

    private fun getState(state: String): LiveData<State> {
        getDistrictsData(state)
        getDailyData(state)
        return repo.getState(state)
    }

    private fun getDistrictsData(state: String) {
        progress.set(View.VISIBLE)
        viewModelScope.launch(Dispatchers.IO) {
            stateDetailsRepository.getDistrictInfo(state, {
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
            stateDetailsRepository.getStateDaily(stateName, { it, max ->
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