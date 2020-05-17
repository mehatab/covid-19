package info.covid.data.models

import com.squareup.moshi.Json

data class Tested(
    @field:Json(name = "source")
    var source: String? = null,
    @field:Json(name = "totalindividualstested")
    var totalindividualstested: String? = null,
    @field:Json(name = "totalpositivecases")
    var totalpositivecases: String? = null,
    @field:Json(name = "totalsamplestested")
    var totalsamplestested: String? = null,
    @field:Json(name = "updatetimestamp")
    var updatetimestamp: String? = null
)