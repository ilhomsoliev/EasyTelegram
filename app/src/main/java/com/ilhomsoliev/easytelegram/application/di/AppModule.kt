package com.ilhomsoliev.easytelegram.application.di

import android.content.Context
import android.os.Build
import com.ilhomsoliev.easytelegram.R
import com.ilhomsoliev.shared.country.CountryManager
import com.ilhomsoliev.tgcore.TdLibParameters
import com.ilhomsoliev.tgcore.TelegramClient
import org.koin.dsl.module
import java.util.Locale


fun telegramModule(context: Context) = module {
    single<TdLibParameters> {
        TdLibParameters(
            // Obtain application identifier hash for Telegram API access at https://my.telegram.org
            apiId = context.resources.getInteger(R.integer.telegram_api_id),
            apiHash = context.getString(R.string.telegram_api_hash),
            useMessageDatabase = true,
            useSecretChats = true,
            systemLanguageCode = Locale.getDefault().language,
            databaseDirectory = context.filesDir.absolutePath,
            deviceModel = Build.MODEL,
            applicationVersion = "0.1",
            enableStorageOptimizer = true,
        )
    }
    single<TelegramClient> {
        TelegramClient(get<TdLibParameters>())
    }
}