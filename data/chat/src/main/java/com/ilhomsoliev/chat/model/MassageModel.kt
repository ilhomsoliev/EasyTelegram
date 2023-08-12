package com.ilhomsoliev.chat.model

import com.ilhomsoliev.profile.ProfileRepository
import org.drinkless.td.libcore.telegram.TdApi

data class MessageModel(
    val id: Long,
    val chatId: Long,
    val sendingStateModel: MessageSendingStateModel,
    val schedulingStateModel: MessageSchedulingStateModel,
    val isOutgoing: Boolean,
    val isPinned: Boolean,
    val canBeEdited: Boolean,
    val canBeForwarded: Boolean,
    val canBeSaved: Boolean,
    val canBeDeletedOnlyForSelf: Boolean,
    val canBeDeletedForAllUsers: Boolean,
    val canGetStatistics: Boolean,
    val canGetMessageThread: Boolean,
    val canGetViewers: Boolean,
    val canGetMediaTimestampLinks: Boolean,
    val hasTimestampedMedia: Boolean,
    val isChannelPost: Boolean,
    val containsUnreadMention: Boolean,
    val date: Int,
    val editDate: Int,
    val sender: com.ilhomsoliev.profile.model.UserModel,
)

suspend fun TdApi.Message.map(
    userRepository: ProfileRepository,
) =
    MessageModel(
        id = id,
        chatId = chatId,
        sendingStateModel = sendingState.map(),
        schedulingStateModel = schedulingState.map(),
        isOutgoing = isOutgoing,
        isPinned = isPinned,
        canBeEdited = canBeEdited,
        canBeForwarded = canBeForwarded,
        canBeSaved = canBeSaved,
        canBeDeletedOnlyForSelf = canBeDeletedOnlyForSelf,
        canBeDeletedForAllUsers = canBeDeletedForAllUsers,
        canGetStatistics = canGetStatistics,
        canGetMessageThread = canGetMessageThread,
        canGetViewers = canGetViewers,
        canGetMediaTimestampLinks = canGetMediaTimestampLinks,
        hasTimestampedMedia = hasTimestampedMedia,
        isChannelPost = isChannelPost,
        containsUnreadMention = containsUnreadMention,
        date = date,
        editDate = editDate,
        sender =userRepository.getUser((senderId as TdApi.MessageSenderUser).userId)
    )