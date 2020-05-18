package info.covid.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import info.covid.data.dao.CovidDao
import info.covid.data.dao.FilterDao
import info.covid.data.dao.ResourcesDao
import info.covid.data.enities.CovidDayInfo
import info.covid.data.enities.KeyValues
import info.covid.data.enities.Resources
import info.covid.data.enities.State

@Database(
    entities = [CovidDayInfo::class, State::class, KeyValues::class, Resources::class],
    version = 4
)
abstract class CovidDb : RoomDatabase() {

    abstract fun getCovidDao(): CovidDao
    abstract fun getResourcesDao(): ResourcesDao
    abstract fun getFilterDao(): FilterDao

}