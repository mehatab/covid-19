package info.covid.data.enities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "TODAY")
data class KeyValues (
    @PrimaryKey
    @ColumnInfo(name = "TodayID")
    var TodayID : Long=0,
    @ColumnInfo(name = "confirmeddelta")
    @field:Json(name = "confirmeddelta")
    var confirmeddelta : String?=null,
    @ColumnInfo(name = "deceaseddelta")
    @field:Json(name = "deceaseddelta")
    var deceaseddelta : String?=null,
    @ColumnInfo(name = "lastupdatedtime")
    @field:Json(name = "lastupdatedtime")
    var lastupdatedtime : String?=null,
    @ColumnInfo(name = "recovereddelta")
    @field:Json(name = "recovereddelta")
    var recovereddelta : String?=null,
    @ColumnInfo(name = "statesdelta")
    @field:Json(name = "statesdelta")
    var statesdelta: String?=null
)