package co.kecker.zilch.di

import co.kecker.zilch.viewmodel.HomeViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val provideviewModelModule = module {
    viewModelOf(::HomeViewModel)
}

fun appModule() = listOf(provideviewModelModule)