package com.ilhomsoliev.shared.shared.utils

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ilhomsoliev.chat.model.chat.ChatModel

fun getChatEmptyProfileName(name: String): String {
    if (name.isEmpty()) return ""

    val spaceAmount = name.count { it == ' ' }
    return if (spaceAmount == 0) {
        name[0].toUpperCase().toString()
    } else {
        val splitedName: List<String> = name.split(' ', limit = 2)
        ((splitedName[0].getOrNull(0) ?: "").toString() + (splitedName[1].getOrNull(0)
            ?: "").toString().toUpperCase())
    }
}

fun getPinnedModifier(chat: ChatModel) =
    if (chat.positions?.get(0)?.isPinned == true) Modifier.background(
        Color(0xFFEFEFEF)
    ) else Modifier
