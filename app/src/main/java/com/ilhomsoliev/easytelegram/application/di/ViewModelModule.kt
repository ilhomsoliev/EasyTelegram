package com.ilhomsoliev.easytelegram.application.di

import com.ilhomsoliev.chat.viewmodel.ChatViewModel
import com.ilhomsoliev.home.viewmodel.HomeViewModel
import com.ilhomsoliev.home.viewmodel.NewMessagesViewModel
import com.ilhomsoliev.login.viewmodel.ChooseCountryViewModel
import com.ilhomsoliev.login.viewmodel.LoginViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@OptIn(ExperimentalCoroutinesApi::class)
val viewModelModule = module {
    viewModel { HomeViewModel(get(), get(), get(), get()) }
    viewModel { NewMessagesViewModel(get(), get(),) }

    viewModel { LoginViewModel(get(), get()) }
    viewModel { ChooseCountryViewModel(get()) }
    viewModel { ChatViewModel(get(), get(), get(), get()) }
}
