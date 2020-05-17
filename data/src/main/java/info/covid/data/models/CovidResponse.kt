package info.covid.data.models

import com.squareup.moshi.Json
import info.covid.data.enities.CovidDayInfo
import info.covid.data.enities.KeyValues
import info.covid.data.enities.State

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