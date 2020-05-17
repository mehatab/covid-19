package info.covid.essentials.di

import info.covid.essentials.EssentialsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val essentialsModule = module {
    viewModel { EssentialsViewModel(get()) }
}