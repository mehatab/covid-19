package info.covid.state.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import info.covid.database.CovidDb

class StateListViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = CovidDb.get(application).getCovidDao()

    val states = dao.getStates()
}