package com.ilhomsoliev.easytelegram.application

import android.app.Application
import com.ilhomsoliev.easytelegram.application.di.managerModule
import com.ilhomsoliev.easytelegram.application.di.repositoryModule
import com.ilhomsoliev.easytelegram.application.di.telegramModule
import com.ilhomsoliev.easytelegram.application.di.viewModelModule
import org.koin.core.context.startKoin

class EasyTelegramApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                listOf(
                    telegramModule(this@EasyTelegramApplication),
                    repositoryModule,
                    viewModelModule,
                    managerModule(this@EasyTelegramApplication),
                )
            )
        }
    }
}
