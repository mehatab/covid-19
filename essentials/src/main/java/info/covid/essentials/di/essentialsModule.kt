package info.covid.essentials.di

import info.covid.essentials.EssentialsViewModel
import info.covid.essentials.filter.EssentialFilterViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val essentialsModule = module {
    viewModel { EssentialsViewModel(get()) }
    viewModel { EssentialFilterViewModel(get()) }
}