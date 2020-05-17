package info.covid.essentials


import androidx.lifecycle.*
import info.covid.data.models.Filter
import info.covid.data.repositories.EssentialsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EssentialsViewModel(val essentialsRepository: EssentialsRepository) : ViewModel() {

    val error = MutableLiveData<String>()
    val refreshing = MutableLiveData(false)

    val filter = MutableLiveData<Filter>()

    val resources = Transformations.switchMap(filter) {
        if (it.categories.isNullOrEmpty())
            essentialsRepository.getLocalResources(it.getStateOrNull(), it.getCityOrNull())
        else essentialsRepository.getLocalResources(it.getStateOrNull(), it.getCityOrNull(), it.getCatOrNull())
    }

    init {
        syncData()
    }

    private fun syncData() {
        viewModelScope.launch(Dispatchers.IO) {
            if (essentialsRepository.getCount() == 0)
                getResources()
        }
    }


    fun getResources() {
        refreshing.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            essentialsRepository.getResources({
                viewModelScope.launch(Dispatchers.IO) {
                    if (it.isNullOrEmpty().not()) {
                        essentialsRepository.clearAll()
                        essentialsRepository.insert(it!!)
                    }
                }
                refreshing.postValue(false)
            }, {
                error.postValue(it)
                refreshing.postValue(false)
            })
        }
    }
}