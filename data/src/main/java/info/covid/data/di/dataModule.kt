package info.covid.data.di

import androidx.room.Room
import info.covid.data.CovidDb
import info.covid.data.MIGRATION_1_2
import info.covid.data.MIGRATION_2_3
import info.covid.data.MIGRATION_3_4
import info.covid.data.repositories.EssentialsRepository
import info.covid.data.repositories.HomeRepository
import info.covid.data.repositories.NotificationRepository
import info.covid.data.repositories.StateRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single { NotificationRepository() }
    single { HomeRepository(get()) }
    single { StateRepository(get()) }
    single { EssentialsRepository(get(), get()) }

    single { get<CovidDb>().getCovidDao() }
    single { get<CovidDb>().getFilterDao() }
    single { get<CovidDb>().getResourcesDao() }

    single {

        Room.databaseBuilder(androidContext(), CovidDb::class.java, "coviddb.db")
            .addMigrations(MIGRATION_2_3)
            .addMigrations(MIGRATION_1_2)
            .addMigrations(MIGRATION_3_4)
            .build()

    }
}