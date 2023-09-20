package com.ilhomsoliev.chat.model.chat

import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.chat.model.message.map
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.ChatPosition

data class ChatModel(
    val id: Long,
    val type: ChatTypeModel,
    val title: String,
    val photo: ChatPhotoInfoModel?,
    val permissions: ChatPermissionsModel,
    val lastMessage: MessageModel?,
    var positions: List<ChatPosition?>?,
    /*@Nullable public MessageSender messageSenderId;*/
    val hasProtectedContent: Boolean,
    val isMarkedAsUnread: Boolean,
    val blockList: BlockListModel,
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
    val themeName: String,
    /*@Nullable public ChatActionBar actionBar;*/
    /*public VideoChat videoChat;*/
    /*@Nullable public ChatJoinRequestsInfo pendingJoinRequests;*/
    val replyMarkupMessageId: Long,
    /*    var draftMessage: DraftMessage? = null*/
    /*public String clientData;*/
)

sealed class BlockListModel {
    object BlockListMainModel : BlockListModel()
    object BlockListStoriesModel : BlockListModel()
}

fun TdApi.BlockList.map(): BlockListModel {
    return if (this is TdApi.BlockListMain) {
        BlockListModel.BlockListMainModel
    } else {
        BlockListModel.BlockListStoriesModel
    }
}

suspend fun TdApi.Chat.map() = ChatModel(
    id = id,
    type = type.map(),
    title = title,
    photo = photo?.map(),
    permissions = permissions.map(),
    lastMessage = lastMessage?.map(),
    positions = positions.toList(),
    hasProtectedContent = hasProtectedContent,
    isMarkedAsUnread = isMarkedAsUnread,
    blockList = blockList?.map() ?: BlockListModel.BlockListMainModel,
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
    themeName = themeName,
    replyMarkupMessageId = replyMarkupMessageId,
)