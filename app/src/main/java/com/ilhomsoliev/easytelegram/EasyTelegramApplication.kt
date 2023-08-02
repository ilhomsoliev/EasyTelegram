package com.ilhomsoliev.easytelegram

import android.app.Application
import android.os.Build
import com.ilhomsoliev.data.TelegramClient
import com.ilhomsoliev.data.chats.ChatsPagingSource
import com.ilhomsoliev.data.chats.ChatsRepository
import com.ilhomsoliev.data.messages.MessagesRepository
import com.ilhomsoliev.login.viewmodel.LoginViewModel
import com.ilhomsoliev.chat.viewmodel.ChatViewModel
import com.ilhomsoliev.home.viewmodel.HomeViewModel
import com.ilhomsoliev.login.viewmodel.ChooseCountryViewModel
import com.ilhomsoliev.shared.country.CountryManager
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
                    viewModelModule,
                    repositoryModule,
                    module {
                        single<TdApi.TdlibParameters> {
                            TdApi.TdlibParameters().apply {
                                // Obtain application identifier hash for Telegram API access at https://my.telegram.org
                                apiId = this@EasyTelegramApplication.resources.getInteger(R.integer.telegram_api_id)
                                apiHash = this@EasyTelegramApplication.getString(R.string.telegram_api_hash)
                                useMessageDatabase = true
                                useSecretChats = true
                                systemLanguageCode = Locale.getDefault().language
                                databaseDirectory =
                                    this@EasyTelegramApplication.filesDir.absolutePath
                                deviceModel = Build.MODEL
                                systemVersion = Build.VERSION.RELEASE
                                applicationVersion = "0.1"
                                enableStorageOptimizer = true
                            }
                        }
                        single {
                            TelegramClient(get<TdApi.TdlibParameters>())
                        }
                        single<CountryManager> { CountryManager(this@EasyTelegramApplication) }
                    }
                )
            )

        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { ChooseCountryViewModel(get()) }
    viewModel { ChatViewModel(get(), get(), get()) }
}

@OptIn(ExperimentalCoroutinesApi::class)
val repositoryModule = module {
    single { ChatsRepository(get()) }
    single { ChatsPagingSource(get()) }
    single { MessagesRepository(get()) }
}
