package info.covid.state.di

import info.covid.state.StateDetailsViewModel
import info.covid.state.country.CountryDetailsViewModel
import info.covid.state.list.StateListViewModel
import info.covid.state.world.GlobalCovidStatusViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val stateModule = module {
    viewModel { StateDetailsViewModel(get(), get()) }
    viewModel { StateListViewModel(get()) }
    viewModel { GlobalCovidStatusViewModel(get()) }
    viewModel { CountryDetailsViewModel(get()) }
}