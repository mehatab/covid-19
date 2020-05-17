package info.covid.data.models

import com.squareup.moshi.Json
import info.covid.data.DiffItem

data class Notification(
    @field:Json(name = "update")
    var update: String? = null,
    @field:Json(name = "timestamp")
    var timestamp: Long? = null
) : DiffItem {
    override fun getId() = 0L

    override fun getContent() = toString()
}