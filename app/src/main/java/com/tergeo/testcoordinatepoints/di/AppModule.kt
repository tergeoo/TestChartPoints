package com.tergeo.testcoordinatepoints.di

import com.tergeo.testcoordinatepoints.presentation.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    viewModelOf(::MainViewModel)
}