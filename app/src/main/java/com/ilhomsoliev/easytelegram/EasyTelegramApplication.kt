package com.ilhomsoliev.easytelegram

import android.app.Application
import android.content.Context
import android.os.Build
import com.ilhomsoliev.auth.AuthRepository
import com.ilhomsoliev.chat.ChatsPagingSource
import com.ilhomsoliev.chat.ChatsRepository
import com.ilhomsoliev.chat.messages.MessagesRepository
import com.ilhomsoliev.chat.viewmodel.ChatViewModel
import com.ilhomsoliev.home.viewmodel.HomeViewModel
import com.ilhomsoliev.login.viewmodel.ChooseCountryViewModel
import com.ilhomsoliev.login.viewmodel.LoginViewModel
import com.ilhomsoliev.profile.ProfileRepository
import com.ilhomsoliev.shared.TgDownloadManager
import com.ilhomsoliev.shared.country.CountryManager
import com.ilhomsoliev.tgcore.TelegramClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.drinkless.td.libcore.telegram.TdApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.util.Locale

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

@OptIn(ExperimentalCoroutinesApi::class)
val viewModelModule = module {
    viewModel { HomeViewModel(get(), get(), get(), get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { ChooseCountryViewModel(get()) }
    viewModel { ChatViewModel(get(), get(), get(), get()) }
}

@OptIn(ExperimentalCoroutinesApi::class)
val repositoryModule = module {
    single { ChatsRepository(get(), get(), get()) }
    single { AuthRepository(get()) }
    single { ChatsPagingSource(get()) }
    single { MessagesRepository(get()) }
    single { ProfileRepository(get()) }
}

fun managerModule(context: Context) = module {
    single {
        TgDownloadManager(get())
    }
}

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