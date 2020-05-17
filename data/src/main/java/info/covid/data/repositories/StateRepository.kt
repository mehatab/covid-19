package info.covid.data.repositories


import info.covid.data.dao.CovidDao

class StateRepository(val dao: CovidDao) {


    fun getStates() = dao.getStates()
    fun getTotal() = dao.getTotal()
    fun getInfo() = dao.getInfo()
    fun getState(state: String) = dao.getState(state)

}