package info.covid.essentials.filter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import info.covid.database.CovidDb

class EssentialFilterViewModel(application: Application) : AndroidViewModel(application) {
    private val filterDao by lazy {
        CovidDb.get(application).getFilterDao()
    }


    val selectedState = MutableLiveData<String>()
    val selectedCity = MutableLiveData<String>()

    val states = filterDao.getStatesAndUT()
    val cities = Transformations.switchMap(selectedState, ::getCities)
    val categories = Transformations.switchMap(selectedCity, ::getCategories)

    private fun getCities(state: String) = filterDao.getCities(state)
    private fun getCategories(city: String) = filterDao.getCategories(city)

    fun onStateSelected(index: Int) {
        selectedCity.postValue("")
        selectedState.postValue(
            if (index >= 0) {
                val filter = states.value?.get(index)?.name ?: ""
                filter
            } else ""
        )
    }


    fun onCitySelected(index: Int) {
        selectedCity.postValue(
            if (index >= 0) {
                val filter = cities.value?.get(index)?.name ?: ""
                filter
            } else ""
        )
    }

}