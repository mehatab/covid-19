package info.covid.data.models

import com.squareup.moshi.Json

data class StateDailyResponse(
    @field:Json(name = "states_daily")
    val dailyStats: List<StateDailyItem>? = null
)