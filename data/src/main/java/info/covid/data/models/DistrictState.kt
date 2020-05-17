package info.covid.data.models

import com.squareup.moshi.Json

data class DistrictState(
    @field:Json(name = "state")
    val state: String? = null,
    @field:Json(name = "districtData")
    val districts: List<District>
)