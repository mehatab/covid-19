package info.covid.state.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import info.covid.customview.DataPoint
import info.covid.database.CovidDb
import info.covid.utils.toNumber
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.Map
import kotlin.collections.arrayListOf
import kotlin.collections.forEachIndexed
import kotlin.collections.map
import kotlin.collections.maxBy
import kotlin.collections.set

class StateListViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = CovidDb.get(application).getCovidDao()

    val states = dao.getStates()


    val total = dao.getTotal()

    var maxValue = 0f

    val dayList: LiveData<Map<String, ArrayList<DataPoint>>> = Transformations.map(dao.getInfo()) {
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