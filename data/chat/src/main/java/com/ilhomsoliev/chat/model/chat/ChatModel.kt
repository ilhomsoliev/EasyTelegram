package com.ilhomsoliev.chat.model.chat

import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.chat.model.message.map
import org.drinkless.td.libcore.telegram.TdApi
import org.drinkless.td.libcore.telegram.TdApi.ChatPosition

data class ChatModel(
    val id: Long,
    val type: ChatTypeModel,
    val title: String,
    val photo: ChatPhotoInfoModel?,
    val permissions: ChatPermissionsModel,
    val lastMessage: MessageModel?,
    var positions: Array<ChatPosition?>?,
    /*@Nullable public MessageSender messageSenderId;*/
    val hasProtectedContent: Boolean,
    val isMarkedAsUnread: Boolean,
    val isBlocked: Boolean,
    val hasScheduledMessages: Boolean,
    val canBeDeletedOnlyForSelf: Boolean,
    val canBeDeletedForAllUsers: Boolean,
    val canBeReported: Boolean,
    val defaultDisableNotification: Boolean,
    val unreadCount: Int,
    val lastReadInboxMessageId: Long,
    val lastReadOutboxMessageId: Long,
    val unreadMentionCount: Int,
    val notificationSettings: ChatNotificationSettingsModel,
    val messageTtl: Int,
    val themeName: String,
    /*@Nullable public ChatActionBar actionBar;*/
    /*public VideoChat videoChat;*/
    /*@Nullable public ChatJoinRequestsInfo pendingJoinRequests;*/
    val replyMarkupMessageId: Long,
    /*    var draftMessage: DraftMessage? = null*/
    /*public String clientData;*/
) {
}

suspend fun TdApi.Chat.map() = ChatModel(
    id = id,
    type = type.map(),
    title = title,
    photo = photo?.map(),
    permissions = permissions.map(),
    lastMessage = lastMessage?.map(null),
    positions = positions,
    hasProtectedContent = hasProtectedContent,
    isMarkedAsUnread = isMarkedAsUnread,
    isBlocked = isBlocked,
    hasScheduledMessages = hasScheduledMessages,
    canBeDeletedOnlyForSelf = canBeDeletedOnlyForSelf,
    canBeDeletedForAllUsers = canBeDeletedForAllUsers,
    canBeReported = canBeReported,
    defaultDisableNotification = defaultDisableNotification,
    unreadCount = unreadCount,
    lastReadInboxMessageId = lastReadInboxMessageId,
    lastReadOutboxMessageId = lastReadOutboxMessageId,
    unreadMentionCount = unreadMentionCount,
    notificationSettings = notificationSettings.map(),
    messageTtl = messageTtl,
    themeName = themeName,
    replyMarkupMessageId = replyMarkupMessageId,
)