package info.covid.data.enities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import info.covid.data.DiffItem

@Entity(tableName = "STATE_WISE",
    indices = [Index(value = ["state"], unique = true)])
data class State(
    @PrimaryKey
    @ColumnInfo(name = "StateID")
    var stateId : Long?=null,
    @ColumnInfo(name = "active")
    @field:Json(name = "active")
    var active: String? = null,
    @ColumnInfo(name = "confirmed")
    @field:Json(name = "confirmed")
    var confirmed: String? = null,
    @ColumnInfo(name = "deaths")
    @field:Json(name = "deaths")
    var deaths: String? = null,
    @ColumnInfo(name = "lastupdatedtime")
    @field:Json(name = "lastupdatedtime")
    var lastupdatedtime: String? = null,
    @ColumnInfo(name = "recovered")
    @field:Json(name = "recovered")
    var recovered: String? = null,
    @ColumnInfo(name = "state")
    @field:Json(name = "state")
    var state: String? = null,
    @ColumnInfo(name = "deltaconfirmed")
    @field:Json(name = "deltaconfirmed")
    var deltaconfirmed : String?=null,
    @ColumnInfo(name = "deltadeaths")
    @field:Json(name = "deltadeaths")
    var deltadeaths : String?=null,
    @ColumnInfo(name = "deltarecovered")
    @field:Json(name = "deltarecovered")
    var deltarecovered : String?=null
) : DiffItem {
    override fun getId() = stateId?:0
    override fun getContent() = toString()
}