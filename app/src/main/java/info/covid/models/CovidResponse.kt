package info.covid.models

import com.squareup.moshi.Json
import info.covid.database.enities.CovidDayInfo
import info.covid.database.enities.KeyValues
import info.covid.database.enities.State

data class CovidResponse(
    @field:Json(name = "cases_time_series")
    var result: List<CovidDayInfo>? = null,
    @field:Json(name = "key_values")
    var key_values: List<KeyValues>? = null,
    @field:Json(name = "statewise")
    var statewise: List<State>? = null,
    @field:Json(name = "tested")
    var tested: List<Tested>? = null
)