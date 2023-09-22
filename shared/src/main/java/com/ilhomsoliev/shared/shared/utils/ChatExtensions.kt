package com.ilhomsoliev.shared.shared.utils

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ilhomsoliev.chat.model.chat.ChatModel
import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.shared.common.extensions.LocalDate
import com.ilhomsoliev.shared.common.extensions.getChatDateSeparator
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Calendar

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

fun getDateSeparatorForMessages(
    message: MessageModel,
    nextMessage: MessageModel?,
    index: Int,
    sizeOfList: Int,
) = if (nextMessage != null &&
    LocalDate(message.date.toLong() * 1000).format("YYYY-MM-dd") != LocalDate(
        nextMessage.date.toLong() * 1000
    ).format(
        "YYYY-MM-dd"
    )
) {
    getChatDateSeparator(message.date.toLong() * 1000)
} else if (index + 1 == sizeOfList) {
    getChatDateSeparator(message.date.toLong() * 1000)
} else null


fun getPinnedModifier(chat: ChatModel) =
    if (chat.positions?.getOrNull(0)?.isPinned == true) Modifier.background(
        Color(0xFFEFEFEF)
    ) else Modifier

fun timeAgoOrFormattedDate(seconds: Int): String {
    return when {
        isSecondsInToday(seconds.toLong()) -> {
            val sdf = SimpleDateFormat("hh.mm aa", Locale.getDefault())
            val date = Date(seconds * 1000L)
            sdf.format(date)
        }

        else -> {
            val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val date = Date(seconds * 1000L)
            sdf.format(date)
        }
    }
}
fun isSecondsInToday(seconds: Long): Boolean {
    // Create a Calendar instance and set it to the current date and time
    val calendar = Calendar.getInstance()

    // Set the time of the Calendar instance to the provided seconds
    calendar.timeInMillis = seconds * 1000 // Convert seconds to milliseconds

    // Get the current date
    val currentDate = Calendar.getInstance()

    // Check if the date from the provided seconds matches the current date
    return calendar.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR) &&
            calendar.get(Calendar.DAY_OF_YEAR) == currentDate.get(Calendar.DAY_OF_YEAR)
}