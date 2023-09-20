package com.ilhomsoliev.core

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object Constants {
    const val CHATS_LIST_THRESHOLD = 15
    const val MESSAGES_LIST_THRESHOLD = 20
}

inline fun <reified T> getKoinInstance(): T {
    return object : KoinComponent {
        val value: T by inject()
    }.value
}