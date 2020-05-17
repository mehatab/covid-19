package info.covid.state.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import info.covid.data.models.DataPoint
import info.covid.data.repositories.StateRepository
import info.covid.data.utils.toNumber
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.Map
import kotlin.collections.arrayListOf
import kotlin.collections.forEachIndexed
import kotlin.collections.map
import kotlin.collections.maxBy
import kotlin.collections.set

class StateListViewModel(val repo: StateRepository) : ViewModel() {

    val states = repo.getStates()


    val total = repo.getTotal()

    var maxValue = 0f

    val dayList: LiveData<Map<String, ArrayList<DataPoint>>> = Transformations.map(repo.getInfo()) {
        val mapData = HashMap<String, ArrayList<DataPoint>>()
        maxValue = it.maxBy { it.totalconfirmed.toNumber() }?.totalconfirmed.toNumber().toFloat()

        mapData["Confirmed"] = it.map { item ->
            DataPoint(
                item.totalconfirmed.toNumber().toFloat()
            )
        } as ArrayList<DataPoint>

        mapData["Deceased"] = it.map { item ->
            DataPoint(
                item.totaldeceased.toNumber().toFloat()
            )
        } as ArrayList<DataPoint>

        mapData["Recovered"] = it.map { item ->
            DataPoint(
                item.totalrecovered.toNumber().toFloat()
            )
        } as ArrayList<DataPoint>

        mapData["Active"] = arrayListOf()

        mapData["Confirmed"]?.forEachIndexed { index, dataPoint ->
            mapData["Active"]?.add(
                DataPoint(
                    dataPoint.amount.minus(
                        mapData["Deceased"]?.get(index)?.amount?.plus(
                            mapData["Recovered"]?.get(index)?.amount ?: 0f
                        ) ?: 0f
                    )
                )
            )
        }

        return@map mapData
    }

}