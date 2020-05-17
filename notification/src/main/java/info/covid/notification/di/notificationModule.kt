package info.covid.notification.di

import info.covid.notification.NotificationViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val notificationModule = module{
    viewModel { NotificationViewModel(get()) }
}