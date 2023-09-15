package com.ilhomsoliev.easytelegram.application.di

import android.content.Context
import android.os.Build
import com.ilhomsoliev.easytelegram.R
import com.ilhomsoliev.shared.country.CountryManager
import com.ilhomsoliev.tgcore.TelegramClient
import org.drinkless.td.libcore.telegram.TdApi
import org.koin.dsl.module
import java.util.Locale


fun telegramModule(context: Context) = module {
    single<TdApi.TdlibParameters> {
        TdApi.TdlibParameters().apply {
            // Obtain application identifier hash for Telegram API access at https://my.telegram.org
            apiId = context.resources.getInteger(R.integer.telegram_api_id)
            apiHash = context.getString(R.string.telegram_api_hash)
            useMessageDatabase = true
            useSecretChats = true
            systemLanguageCode = Locale.getDefault().language
            databaseDirectory = context.filesDir.absolutePath
            deviceModel = Build.MODEL
            systemVersion = Build.VERSION.RELEASE
            applicationVersion = "0.1"
            enableStorageOptimizer = true
        }
    }
    single<TelegramClient> {
        TelegramClient(get<TdApi.TdlibParameters>())
    }
    single<CountryManager> { CountryManager(context) }
}