package com.ilhomsoliev.chat.model.chat

import org.drinkless.tdlib.TdApi

data class ChatPermissionsModel(
    val canSendBasicMessages: Boolean,
    val canSendAudios: Boolean,
    val canSendDocuments: Boolean,
    val canSendPhotos: Boolean,
    val canSendVideos: Boolean,
    val canSendVideoNotes: Boolean,
    val canSendVoiceNotes: Boolean,
    val canSendPolls: Boolean,
    val canSendOtherMessages: Boolean,
    val canAddWebPagePreviews: Boolean,
    val canChangeInfo: Boolean,
    val canInviteUsers: Boolean,
    val canPinMessages: Boolean,
    val canManageTopics: Boolean,
)

fun TdApi.ChatPermissions.map() = ChatPermissionsModel(
    canSendBasicMessages = canSendBasicMessages,
    canSendAudios = canSendAudios,
    canSendDocuments = canSendDocuments,
    canSendPhotos = canSendPhotos,
    canSendVideos = canSendVideos,
    canSendVideoNotes = canSendVideoNotes,
    canSendVoiceNotes = canSendVoiceNotes,
    canSendPolls = canSendPolls,
    canSendOtherMessages = canSendOtherMessages,
    canAddWebPagePreviews = canAddWebPagePreviews,
    canChangeInfo = canChangeInfo,
    canInviteUsers = canInviteUsers,
    canPinMessages = canPinMessages,
    canManageTopics = canManageTopics,
)