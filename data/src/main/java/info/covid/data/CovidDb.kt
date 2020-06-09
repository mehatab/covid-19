package info.covid.data


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import info.covid.data.converters.CountryCodeConverters
import info.covid.data.dao.CovidDao
import info.covid.data.dao.FilterDao
import info.covid.data.dao.GlobalDao
import info.covid.data.dao.ResourcesDao
import info.covid.data.enities.CovidDayInfo
import info.covid.data.enities.KeyValues
import info.covid.data.enities.Resources
import info.covid.data.enities.State
import info.covid.data.models.global.Country
import info.covid.data.models.global.CountryInfo

@Database(
    entities = [CovidDayInfo::class, State::class, KeyValues::class, Resources::class, Country::class, CountryInfo::class],
    version = 5
)
@TypeConverters(CountryCodeConverters::class)
abstract class CovidDb : RoomDatabase() {

    abstract fun getCovidDao(): CovidDao
    abstract fun getResourcesDao(): ResourcesDao
    abstract fun getFilterDao(): FilterDao
    abstract fun getGlobalDao(): GlobalDao

}