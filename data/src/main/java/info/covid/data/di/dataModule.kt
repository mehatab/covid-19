package info.covid.data.di

import androidx.room.Room
import info.covid.data.*
import info.covid.data.network.*
import info.covid.data.network.RetrofitClient.BASE_URL
import info.covid.data.network.RetrofitClient.GLOBAL_BASE_URL
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
    single { GlobalStatusRepository(get(), get()) }


    //Retrofit api service
    single { provideRetrofit() }
    factory { provideCovidApi(get()) }
    factory { provideEssentialsApi(get()) }
    factory { provideStateApi(get()) }
    factory { provideGlobalApi(provideGlobalRetrofit()) }


    //Local database
    single { get<CovidDb>().getCovidDao() }
    single { get<CovidDb>().getFilterDao() }
    single { get<CovidDb>().getResourcesDao() }
    single { get<CovidDb>().getGlobalDao() }

    single {
        Room.databaseBuilder(androidContext(), CovidDb::class.java, "coviddb.db")
            .addMigrations(MIGRATION_2_3)
            .addMigrations(MIGRATION_1_2)
            .addMigrations(MIGRATION_3_4)
            .addMigrations(MIGRATION_4_5)
            .build()

    }

}

private fun provideRetrofit() = RetrofitClient.get()

private fun provideGlobalRetrofit() = RetrofitClient.get(GLOBAL_BASE_URL)

private fun provideCovidApi(retrofit: Retrofit) = retrofit.create(CovidApiService::class.java)

private fun provideEssentialsApi(retrofit: Retrofit) = retrofit.create(EssentialsApiService::class.java)

private fun provideStateApi(retrofit: Retrofit) = retrofit.create(StateAPIService::class.java)

private fun provideGlobalApi(retrofit: Retrofit) = retrofit.create(GlobalApiService::class.java)
