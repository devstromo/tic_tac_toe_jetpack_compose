package com.devstromo.advancedtictactoe

import android.app.Application
import com.devstromo.advancedtictactoe.di.appModule
import com.devstromo.advancedtictactoe.di.notificationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AdvanceTicTacToeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AdvanceTicTacToeApplication)
            modules(listOf(appModule, notificationModule))
        }
    }
}