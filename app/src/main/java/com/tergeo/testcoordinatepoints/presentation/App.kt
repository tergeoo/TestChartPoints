package com.tergeo.testcoordinatepoints.presentation

import android.app.Application
import com.tergeo.testcoordinatepoints.di.appModule
import com.tergeo.testcoordinatepoints.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(appModule, dataModule))
        }
    }
}