package info.covid.state.di

import info.covid.state.StateDetailsViewModel
import info.covid.state.list.StateListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val stateModule = module{
    viewModel { StateDetailsViewModel(get(), get()) }
    viewModel { StateListViewModel(get()) }
}