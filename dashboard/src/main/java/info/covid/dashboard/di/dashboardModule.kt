package info.covid.dashboard.di

import info.covid.dashboard.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dashboardModule = module {
    viewModel { HomeViewModel(get()) }
}