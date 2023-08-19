package com.ilhomsoliev.chat.model.chat

import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.chat.model.message.map
import com.ilhomsoliev.profile.ProfileRepository
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
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChatModel

        if (id != other.id) return false
        if (type != other.type) return false
        if (title != other.title) return false
        if (photo != other.photo) return false
        if (permissions != other.permissions) return false
        if (lastMessage != other.lastMessage) return false
        if (positions != null) {
            if (other.positions == null) return false
            if (!positions.contentEquals(other.positions)) return false
        } else if (other.positions != null) return false
        if (hasProtectedContent != other.hasProtectedContent) return false
        if (isMarkedAsUnread != other.isMarkedAsUnread) return false
        if (isBlocked != other.isBlocked) return false
        if (hasScheduledMessages != other.hasScheduledMessages) return false
        if (canBeDeletedOnlyForSelf != other.canBeDeletedOnlyForSelf) return false
        if (canBeDeletedForAllUsers != other.canBeDeletedForAllUsers) return false
        if (canBeReported != other.canBeReported) return false
        if (defaultDisableNotification != other.defaultDisableNotification) return false
        if (unreadCount != other.unreadCount) return false
        if (lastReadInboxMessageId != other.lastReadInboxMessageId) return false
        if (lastReadOutboxMessageId != other.lastReadOutboxMessageId) return false
        if (unreadMentionCount != other.unreadMentionCount) return false
        if (notificationSettings != other.notificationSettings) return false
        if (messageTtl != other.messageTtl) return false
        if (themeName != other.themeName) return false
        if (replyMarkupMessageId != other.replyMarkupMessageId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + (photo?.hashCode() ?: 0)
        result = 31 * result + permissions.hashCode()
        result = 31 * result + (lastMessage?.hashCode() ?: 0)
        result = 31 * result + (positions?.contentHashCode() ?: 0)
        result = 31 * result + hasProtectedContent.hashCode()
        result = 31 * result + isMarkedAsUnread.hashCode()
        result = 31 * result + isBlocked.hashCode()
        result = 31 * result + hasScheduledMessages.hashCode()
        result = 31 * result + canBeDeletedOnlyForSelf.hashCode()
        result = 31 * result + canBeDeletedForAllUsers.hashCode()
        result = 31 * result + canBeReported.hashCode()
        result = 31 * result + defaultDisableNotification.hashCode()
        result = 31 * result + unreadCount
        result = 31 * result + lastReadInboxMessageId.hashCode()
        result = 31 * result + lastReadOutboxMessageId.hashCode()
        result = 31 * result + unreadMentionCount
        result = 31 * result + notificationSettings.hashCode()
        result = 31 * result + messageTtl
        result = 31 * result + themeName.hashCode()
        result = 31 * result + replyMarkupMessageId.hashCode()
        return result
    }
}

suspend fun TdApi.Chat.map(
    profileRepository: ProfileRepository,
) = ChatModel(
    id = id,
    type = type.map(),
    title = title,
    photo = photo?.map(),
    permissions = permissions.map(),
    lastMessage = lastMessage?.map(profileRepository),
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