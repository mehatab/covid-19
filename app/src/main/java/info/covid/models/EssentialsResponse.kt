package info.covid.models

import com.squareup.moshi.Json
import info.covid.database.enities.Resources

data class EssentialsResponse(
    @field:Json(name = "resources")
    var resources: List<Resources>? = null
)