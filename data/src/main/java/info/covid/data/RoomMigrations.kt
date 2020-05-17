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
