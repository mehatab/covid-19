package info.covid.essentials

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import info.covid.database.CovidDb
import info.covid.models.Filter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EssentialsViewModel(application: Application) : AndroidViewModel(application) {

    private val essentialsRepository = EssentialsRepository()
    val error = MutableLiveData<String>()
    val refreshing = MutableLiveData(false)

    private val resourceDao by lazy {
        CovidDb.get(application).getResourcesDao()
    }

    val filter = MutableLiveData<Filter>()

    val resources = Transformations.switchMap(filter) {
        if (it.categories.isNullOrEmpty())
            resourceDao.getResources(it.getStateOrNull(), it.getCityOrNull())
        else resourceDao.getResources(it.getStateOrNull(), it.getCityOrNull(), it.getCatOrNull())
    }

    init {
        syncData()
    }

    private fun syncData() {
        viewModelScope.launch(Dispatchers.IO) {
            if (resourceDao.getCount() == 0)
                getResources()
        }
    }


    fun getResources() {
        refreshing.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            essentialsRepository.getResources({
                viewModelScope.launch(Dispatchers.IO) {
                    if (it.isNullOrEmpty().not()) {
                        resourceDao.clearAll()
                        resourceDao.insert(it!!)
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