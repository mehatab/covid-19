package info.covid.models

import com.squareup.moshi.Json
import info.covid.common.DiffItem

data class District(
    @field:Json(name = "district")
    var district: String? = null,
    @field:Json(name = "confirmed")
    var confirmed: Int? = 0,
    @field:Json(name = "delta")
    var delta: Delta? = null
) : DiffItem {
    override fun getId() = 0L
    override fun getContent() = toString()
}