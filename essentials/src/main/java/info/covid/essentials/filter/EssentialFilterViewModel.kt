package info.covid.essentials.filter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import info.covid.data.repositories.EssentialsRepository

class EssentialFilterViewModel(val repo: EssentialsRepository) : ViewModel() {


    val selectedState = MutableLiveData<String>()
    val selectedCity = MutableLiveData<String>()

    val states = repo.getStatesAndUT()
    val cities = Transformations.switchMap(selectedState, ::getCities)
    val categories = Transformations.switchMap(selectedCity, ::getCategories)

    private fun getCities(state: String) = repo.getCities(state)
    private fun getCategories(city: String) = repo.getCategories(city)

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