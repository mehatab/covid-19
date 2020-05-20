package info.covid.data.di

import androidx.room.Room
import info.covid.data.CovidDb
import info.covid.data.MIGRATION_1_2
import info.covid.data.MIGRATION_2_3
import info.covid.data.MIGRATION_3_4
import info.covid.data.network.CovidApiService
import info.covid.data.network.EssentialsApiService
import info.covid.data.network.RetrofitClient
import info.covid.data.network.StateAPIService
import info.covid.data.repositories.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val dataModule = module {

    //Repositories
    factory { NotificationRepository(get()) }
    factory { HomeRepository(get(), get()) }
    factory { StateRepository(get()) }
    factory { EssentialsRepository(get(), get(), get()) }
    single { StateDetailsRepository(get()) }


    //Retrofit api service
    single { provideRetrofit() }
    factory { provideCovidApi(get()) }
    factory { provideEssentialsApi(get()) }
    factory { provideStateApi(get()) }


    //Local database
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

private fun provideRetrofit() = RetrofitClient.get()

private fun provideCovidApi(retrofit: Retrofit) = retrofit.create(CovidApiService::class.java)

private fun provideEssentialsApi(retrofit: Retrofit) = retrofit.create(EssentialsApiService::class.java)

private fun provideStateApi(retrofit: Retrofit) = retrofit.create(StateAPIService::class.java)
