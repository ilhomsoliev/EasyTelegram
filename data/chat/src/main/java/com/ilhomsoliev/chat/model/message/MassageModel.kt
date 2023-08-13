package com.ilhomsoliev.chat.model.message

import com.ilhomsoliev.chat.model.message.messageContent.MessageContentModel
import com.ilhomsoliev.chat.model.message.messageContent.NotDefined
import com.ilhomsoliev.chat.model.message.messageContent.messageAnimation.map
import com.ilhomsoliev.chat.model.message.messageContent.messageAudio.map
import com.ilhomsoliev.chat.model.message.messageContent.messageText.map
import com.ilhomsoliev.profile.ProfileRepository
import com.ilhomsoliev.profile.model.UserModel
import org.drinkless.td.libcore.telegram.TdApi
import org.drinkless.td.libcore.telegram.TdApi.MessageAnimation
import org.drinkless.td.libcore.telegram.TdApi.MessageAudio
import org.drinkless.td.libcore.telegram.TdApi.MessageText

data class MessageModel(
    val id: Long,
    val chatId: Long,
    val sender: UserModel,
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
    val replyInChatId: Long,
    val replyToMessageId: Long,
    val messageThreadId: Long,
    val ttl: Int,
    val ttlExpiresIn: Double,
    val viaBotUserId: Long,
    val content: MessageContentModel,
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
        sender = userRepository.getUser((senderId as TdApi.MessageSenderUser).userId),
        replyInChatId = replyInChatId,
        replyToMessageId = replyToMessageId,
        messageThreadId = messageThreadId,
        ttl = ttl,
        ttlExpiresIn = ttlExpiresIn,
        viaBotUserId = viaBotUserId,
        content = content.getMessageTypeModel(),
    )

// TODO add all types
fun TdApi.MessageContent.getMessageTypeModel(): MessageContentModel =
    when (this.constructor) {
        MessageText.CONSTRUCTOR -> (this as MessageText).map()
        MessageAnimation.CONSTRUCTOR -> (this as MessageAnimation).map()
        MessageAudio.CONSTRUCTOR -> (this as MessageAudio).map()
        else -> {
            NotDefined()
        }
    }