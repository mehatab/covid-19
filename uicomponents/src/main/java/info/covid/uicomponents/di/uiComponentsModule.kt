package info.covid.uicomponents.di

import info.covid.uicomponents.GenericRVAdapter
import org.koin.dsl.module

val uiComponentsModule = module {
    factory (override = true) { params ->
        GenericRVAdapter(if (params.size() > 0) params[0] else 0)
    }
}