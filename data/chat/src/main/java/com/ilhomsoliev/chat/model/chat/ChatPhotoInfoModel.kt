package com.ilhomsoliev.chat.model.chat

import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.File
import org.drinkless.tdlib.TdApi.Minithumbnail

data class ChatPhotoInfoModel(
    val small: File,
    val big: File,
    val minithumbnail: Minithumbnail?,
    val hasAnimation: Boolean,
)

fun TdApi.ChatPhotoInfo.map() = ChatPhotoInfoModel(
    small = small,
    big = big,
    minithumbnail = minithumbnail,
    hasAnimation = hasAnimation,
)