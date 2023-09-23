package com.ilhomsoliev.easytelegram.application.di

import android.content.Context
import com.ilhomsoliev.chat.chats.manager.ChatsManager
import com.ilhomsoliev.chat.messages.manager.MessagesManager
import com.ilhomsoliev.profile.manager.ProfileManager
import com.ilhomsoliev.shared.TgDownloadManager
import com.ilhomsoliev.shared.country.CountryManager
import org.koin.dsl.module

fun managerModule(context: Context) = module {
    single { TgDownloadManager(get()) }
    single { ProfileManager(get()) }
    single { ChatsManager(get()) }
    single { MessagesManager(get()) }
    single<CountryManager> { CountryManager(context, get()) }
}