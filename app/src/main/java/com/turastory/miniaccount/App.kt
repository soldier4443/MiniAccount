package com.turastory.miniaccount

import android.app.Application
import com.turastory.miniaccount.di.appModule
import org.koin.android.ext.android.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
    }
}