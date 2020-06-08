package info.covid.data.models.global

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import info.covid.data.DiffItem

@Entity
data class Country(
    @PrimaryKey
    var countryId: Long? = null,
    @field:Json(name = "updated")
    var updated: Long? = null,
    @field:Json(name = "country")
    var country: String? = null,
    @field:Json(name = "cases")
    var cases: Int? = null,
    @field:Json(name = "todayCases")
    var todayCases: Int? = null,
    @field:Json(name = "deaths")
    var deaths: Int? = null,
    @field:Json(name = "todayDeaths")
    var todayDeaths: Int? = null,
    @field:Json(name = "recovered")
    var recovered: Int? = null,
    @field:Json(name = "todayRecovered")
    var todayRecovered: Int? = null,
    @field:Json(name = "active")
    var active: Int? = null,
    @field:Json(name = "critical")
    var critical: Int? = null,
    @field:Json(name = "casesPerOneMillion")
    var casesPerOneMillion: Float? = null,
    @field:Json(name = "deathsPerOneMillion")
    var deathsPerOneMillion: Float? = null,
    @field:Json(name = "tests")
    var tests: Int? = null,
    @field:Json(name = "testsPerOneMillion")
    var testsPerOneMillion: Float? = null,
    @field:Json(name = "population")
    var population: Int? = null,
    @field:Json(name = "continent")
    var continent: String? = null,
    @field:Json(name = "oneCasePerPeople")
    var oneCasePerPeople: Float? = null,
    @field:Json(name = "oneDeathPerPeople")
    var oneDeathPerPeople: Float? = null,
    @field:Json(name = "oneTestPerPeople")
    var oneTestPerPeople: Float? = null,
    @field:Json(name = "activePerOneMillion")
    var activePerOneMillion: Float? = null,
    @field:Json(name = "recoveredPerOneMillion")
    var recoveredPerOneMillion: Float? = null,
    @field:Json(name = "criticalPerOneMillion")
    var criticalPerOneMillion: Float? = null,
    @Embedded
    @field:Json(name = "countryInfo")
    var countryInfo: CountryInfo? = null
) : DiffItem {
    override fun getId() = countryId ?: 0
    override fun getContent() = toString()
}