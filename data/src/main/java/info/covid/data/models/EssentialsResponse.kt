package info.covid.data.models

import com.squareup.moshi.Json
import info.covid.data.enities.Resources

data class EssentialsResponse(
    @field:Json(name = "resources")
    var resources: List<Resources>? = null
)