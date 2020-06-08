package info.covid.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

const val TODAY_TABLE_NAME = "TODAY"
const val STATE_WISE_TABLE_NAME = "STATE_WISE"

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `${TODAY_TABLE_NAME}` (`TodayID` INTEGER NOT NULL, `confirmeddelta` TEXT, `deceaseddelta` TEXT,  `lastupdatedtime` TEXT, `recovereddelta` TEXT, `statesdelta` TEXT, PRIMARY KEY(`TodayID`))")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE `${STATE_WISE_TABLE_NAME}` ADD COLUMN  `deltaconfirmed` TEXT")
        database.execSQL("ALTER TABLE `${STATE_WISE_TABLE_NAME}` ADD COLUMN `deltadeaths` TEXT")
        database.execSQL("ALTER TABLE `${STATE_WISE_TABLE_NAME}` ADD COLUMN  `deltarecovered` TEXT")
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `resources` (`resourceId` INTEGER, `category` TEXT, `city` TEXT,  `contact` TEXT, `organisation` TEXT, `description` TEXT, `phonenumber` TEXT, `state` TEXT, PRIMARY KEY(`resourceId`))")
    }
}

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        val country = """
            CREATE TABLE IF NOT EXISTS `Country` (
                `countryId` INTEGER,
                `updated` INTEGER,
                `country` TEXT,
                `continent` TEXT,
                `cases` INTEGER, 
                `todayCases` INTEGER, 
                `deaths` INTEGER, 
                `todayDeaths` INTEGER, 
                `recovered` INTEGER, 
                `todayRecovered` INTEGER, 
                `active` INTEGER, 
                `critical` INTEGER, 
                `casesPerOneMillion` REAL, 
                `deathsPerOneMillion` REAL, 
                `tests` INTEGER, 
                `population` INTEGER, 
                `testsPerOneMillion` REAL, 
                `oneCasePerPeople` REAL, 
                `oneDeathPerPeople` REAL, 
                `oneTestPerPeople` REAL, 
                `activePerOneMillion` REAL, 
                `recoveredPerOneMillion` REAL, 
                `criticalPerOneMillion` REAL,
                `infoId` INTEGER,
                `iso2` TEXT,
                `iso3` TEXT,
                `flag` TEXT,
                `latitude` REAL, 
                `longitude` REAL,
                PRIMARY KEY(`countryId`)
            )
        """.trimIndent()

        val countryInfo = """
            CREATE TABLE IF NOT EXISTS `CountryInfo` (
                `infoId` INTEGER,
                `iso2` TEXT,
                `iso3` TEXT,
                `flag` TEXT,
                `latitude` REAL, 
                `longitude` REAL,
                PRIMARY KEY(`infoId`)
            )
        """.trimIndent()

        database.execSQL(country)
        database.execSQL(countryInfo)
    }
}
