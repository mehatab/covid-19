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

    companion object {
        private var INSTANCE: CovidDb? = null
        const val TODAY_TABLE_NAME = "TODAY"
        const val STATE_WISE_TABLE_NAME = "STATE_WISE"

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `$TODAY_TABLE_NAME` (`TodayID` INTEGER NOT NULL, `confirmeddelta` TEXT, `deceaseddelta` TEXT,  `lastupdatedtime` TEXT, `recovereddelta` TEXT, `statesdelta` TEXT, PRIMARY KEY(`TodayID`))")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE `$STATE_WISE_TABLE_NAME` ADD COLUMN  `deltaconfirmed` TEXT")
                database.execSQL("ALTER TABLE `$STATE_WISE_TABLE_NAME` ADD COLUMN `deltadeaths` TEXT")
                database.execSQL("ALTER TABLE `$STATE_WISE_TABLE_NAME` ADD COLUMN  `deltarecovered` TEXT")
            }
        }

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `resources` (`resourceId` INTEGER, `category` TEXT, `city` TEXT,  `contact` TEXT, `organisation` TEXT, `description` TEXT, `phonenumber` TEXT, `state` TEXT, PRIMARY KEY(`resourceId`))")
            }
        }

        @JvmStatic
        fun get(context: Context): CovidDb {
            if (INSTANCE == null) {
                INSTANCE =
                    Room.databaseBuilder(context, CovidDb::class.java, "coviddb.db")
                        .addMigrations(MIGRATION_2_3)
                        .addMigrations(MIGRATION_1_2)
                        .addMigrations(MIGRATION_3_4)
                        .build()
            }
            return INSTANCE!!
        }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}