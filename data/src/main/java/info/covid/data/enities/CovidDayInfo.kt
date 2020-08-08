package info.covid.data.enities

import android.view.ViewDebug
import androidx.room.*
import com.squareup.moshi.Json
import info.covid.data.DiffItem
import info.covid.data.utils.toNumber

@Entity(
    tableName = "COVID_INFO_DAY",
    indices = [Index(value = ["date"], unique = true)]
)
data class CovidDayInfo(
    @PrimaryKey
    @ColumnInfo(name = "dayId")
    var dayId: Long? = null,
    @ColumnInfo(name = "date")
    @field:Json(name = "date")
    var date: String? = null,
    @ColumnInfo(name = "dailydeceased")
    @field:Json(name = "dailydeceased")
    var dailydeceased: String? = null,
    @ColumnInfo(name = "dailyconfirmed")
    @field:Json(name = "dailyconfirmed")
    var dailyconfirmed: String? = null,
    @ColumnInfo(name = "dailyrecovered")
    @field:Json(name = "dailyrecovered")
    var dailyrecovered: String? = null,
    @ColumnInfo(name = "totalconfirmed")
    @field:Json(name = "totalconfirmed")
    var totalconfirmed: String? = null,
    @ColumnInfo(name = "totaldeceased")
    @field:Json(name = "totaldeceased")
    var totaldeceased: String? = null,
    @ColumnInfo(name = "totalrecovered")
    @field:Json(name = "totalrecovered")
    var totalrecovered: String? = null
) : DiffItem {
    override fun getId() = dayId ?: 0
    override fun getContent() = toString()
    fun getTotalActive() =
        totalconfirmed.toNumber().minus(totaldeceased.toNumber().plus(totalrecovered.toNumber()))

    fun getDailyActive() =
        dailyconfirmed.toNumber().minus(dailydeceased.toNumber().plus(dailyrecovered.toNumber()))

}