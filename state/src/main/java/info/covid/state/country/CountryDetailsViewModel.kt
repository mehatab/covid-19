package info.covid.state.country


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import info.covid.data.repositories.GlobalStatusRepository

class CountryDetailsViewModel(private val repository: GlobalStatusRepository) : ViewModel() {
    val countryName = MutableLiveData<String>()

    var country = Transformations.switchMap(countryName, ::getCountry)

    private fun getCountry(name: String) = repository.getCountry(name)

}