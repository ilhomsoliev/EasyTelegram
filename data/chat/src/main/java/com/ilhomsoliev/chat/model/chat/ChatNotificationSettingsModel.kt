package com.ilhomsoliev.chat.model.chat

import org.drinkless.td.libcore.telegram.TdApi

data class ChatNotificationSettingsModel(
    val useDefaultMuteFor: Boolean,
    val muteFor: Int,
    val useDefaultSound: Boolean,
    val useDefaultShowPreview: Boolean,
    val showPreview: Boolean,
    val useDefaultDisablePinnedMessageNotifications: Boolean,
    val disablePinnedMessageNotifications: Boolean,
    val useDefaultDisableMentionNotifications: Boolean,
    val disableMentionNotifications: Boolean,
)

fun TdApi.ChatNotificationSettings.map() = ChatNotificationSettingsModel(
    useDefaultMuteFor = useDefaultMuteFor,
    muteFor = muteFor,
    useDefaultSound = useDefaultSound,
    useDefaultShowPreview = useDefaultShowPreview,
    showPreview = showPreview,
    useDefaultDisablePinnedMessageNotifications = useDefaultDisablePinnedMessageNotifications,
    disablePinnedMessageNotifications = disablePinnedMessageNotifications,
    useDefaultDisableMentionNotifications = useDefaultDisableMentionNotifications,
    disableMentionNotifications = disableMentionNotifications,
)