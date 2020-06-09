package info.covid.data.models.global

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import info.covid.data.models.Currency

@Entity
data class CountryInfo(
    @PrimaryKey
    @field:Json(name = "_id")
    var infoId: Long? = null,
    @field:Json(name = "iso2")
    var iso2: Currency? = null,
    @field:Json(name = "iso3")
    var iso3: String? = null,
    @field:Json(name = "flag")
    var flag: String? = null,
    @field:Json(name = "lat")
    var latitude: Float? = null,
    @field:Json(name = "long")
    var longitude: Float? = null
)