package info.covid.data.repositories

import android.app.Application
import info.covid.data.CovidDb

class StateRepository(application: Application) {
    private val dao = CovidDb.get(application).getCovidDao()

    fun getStates() = dao.getStates()
    fun getTotal() = dao.getTotal()
    fun getInfo() = dao.getInfo()
    fun getState(state: String) = dao.getState(state)

}