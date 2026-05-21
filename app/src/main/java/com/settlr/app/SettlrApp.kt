package com.settlr.app

import android.app.Application
import com.settlr.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SettlrApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidContext(this@SettlrApp)
            modules(appModule)
        }
    }
}
