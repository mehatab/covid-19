package info.covid.essentials

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import info.covid.data.models.Filter

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    val filter = MutableLiveData<Filter>()

    fun isFilterSelected(): Boolean {
        return (filter.value != null && filter.value?.stateName.isNullOrEmpty().not())
    }
}