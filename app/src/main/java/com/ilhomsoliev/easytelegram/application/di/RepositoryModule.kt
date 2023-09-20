package com.ilhomsoliev.easytelegram.application.di

import com.ilhomsoliev.auth.AuthRepository
import com.ilhomsoliev.chat.chats.repository.ChatsRepository
import com.ilhomsoliev.chat.messages.repository.MessagesRepository
import com.ilhomsoliev.profile.repository.ProfileRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module


@OptIn(ExperimentalCoroutinesApi::class)
val repositoryModule = module {
    single { ChatsRepository(get(), get(),) }
    single { AuthRepository(get()) }
    single { MessagesRepository(get()) }
    single { ProfileRepository(get()) }
}
