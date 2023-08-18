package com.ilhomsoliev.chat.model.chat

import org.drinkless.td.libcore.telegram.TdApi

data class ChatPermissionsModel(
    val canSendMessages: Boolean,
    val canSendMediaMessages: Boolean,
    val canSendPolls: Boolean,
    val canSendOtherMessages: Boolean,
    val canAddWebPagePreviews: Boolean,
    val canChangeInfo: Boolean,
    val canInviteUsers: Boolean,
    val canPinMessages: Boolean,
)

fun TdApi.ChatPermissions.map() = ChatPermissionsModel(
    canSendMessages = canSendMessages,
    canSendMediaMessages = canSendMediaMessages,
    canSendPolls = canSendPolls,
    canSendOtherMessages = canSendOtherMessages,
    canAddWebPagePreviews = canAddWebPagePreviews,
    canChangeInfo = canChangeInfo,
    canInviteUsers = canInviteUsers,
    canPinMessages = canPinMessages,
)