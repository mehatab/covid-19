package info.covid.data.models

import com.squareup.moshi.Json
import info.covid.data.DiffItem

data class District(
    @field:Json(name = "district")
    var district: String? = null,
    @field:Json(name = "confirmed")
    var confirmed: Int? = 0,
    @field:Json(name = "active")
    var active: Int? = 0,
    @field:Json(name = "deceased")
    var deceased: Int? = 0,
    @field:Json(name = "recovered")
    var recovered: Int? = 0,
    @field:Json(name = "delta")
    var delta: Delta? = null
) : DiffItem {
    override fun getId() = 0L
    override fun getContent() = toString()
}