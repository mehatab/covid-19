package info.covid.data.enities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import info.covid.data.DiffItem

@Entity(tableName = "resources")
data class Resources(
    @PrimaryKey
    @ColumnInfo(name = "resourceId")
    val resourceId: Long? = null,
    @ColumnInfo(name = "category")
    @field:Json(name = "category")
    val category: String? = null,
    @ColumnInfo(name = "city")
    @field:Json(name = "city")
    val city: String? = null,
    @ColumnInfo(name = "contact")
    @field:Json(name = "contact")
    val contact: String? = null,
    @ColumnInfo(name = "organisation")
    @field:Json(name = "nameoftheorganisation")
    val organisation: String? = null,
    @ColumnInfo(name = "description")
    @field:Json(name = "descriptionandorserviceprovided")
    val description: String? = null,
    @ColumnInfo(name = "phonenumber")
    @field:Json(name = "phonenumber")
    val phonenumber: String? = null,
    @ColumnInfo(name = "state")
    @field:Json(name = "state")
    val state: String? = null
) : DiffItem {
    override fun getId() = resourceId ?: 0
    override fun getContent() = toString()
    fun getAddress() = "$city, $state"
}