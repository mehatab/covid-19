package info.covid.data.models

import com.squareup.moshi.Json

data class Delta(
    @field:Json(name = "confirmed")
    var confirmed: Int? = 0,
    @field:Json(name = "deceased")
    var deceased: Int? = 0,
    @field:Json(name = "recovered")
    var recovered: Int? = 0
)