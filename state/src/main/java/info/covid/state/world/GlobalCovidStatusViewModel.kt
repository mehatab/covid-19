package info.covid.state.world

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.covid.data.models.Result
import info.covid.data.models.global.Country
import info.covid.data.repositories.GlobalStatusRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GlobalCovidStatusViewModel(private val repo: GlobalStatusRepository) : ViewModel() {
    val loader = ObservableField(View.VISIBLE)
    val error = MutableLiveData<String>()
    private val _countries = MutableLiveData<List<Country>>()

    val countries: LiveData<List<Country>>
        get() = _countries

    init {
        getCountries()
    }

    private fun getCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllCountryData().collect {
                when (it) {
                    is Result.Success -> {
                        _countries.postValue(it.data)
                    }
                    is Result.Error -> {
                        error.postValue(it.exception.localizedMessage)
                    }
                    is Result.Loading -> {
                        loader.set(if (it.isLoading) View.VISIBLE else View.GONE)
                    }
                }
            }
        }
    }

    fun filter(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _countries.postValue(repo.getAllCountry("%$query%"))
        }
    }
}