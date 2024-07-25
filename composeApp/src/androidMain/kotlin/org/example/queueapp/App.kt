package org.example.queueapp

import android.app.Application
import di.KoinFactory
import org.koin.android.ext.koin.androidContext

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        KoinFactory.setupKoin() {
            androidContext(this@App)
        }
    }
}