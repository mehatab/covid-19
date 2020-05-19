package info.covid

import android.app.Application
import com.bugsnag.android.Bugsnag
import info.covid.dashboard.di.dashboardModule
import info.covid.data.di.dataModule
import info.covid.essentials.di.essentialsModule
import info.covid.notification.di.notificationModule
import info.covid.state.di.stateModule
import info.covid.uicomponents.di.uiComponentsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class CovidApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG.not()) {
            with(Bugsnag.init(this, BuildConfig.BUGSNAG_API_KEY)){
                setReleaseStage(BuildConfig.BUILD_TYPE)
                setProjectPackages("info.covid")
            }
        }


        startKoin {
            androidContext(this@CovidApp)
            modules(
                listOf(
                    dataModule,
                    notificationModule,
                    dashboardModule,
                    essentialsModule,
                    stateModule,
                    uiComponentsModule
                )
            )
        }
    }
}