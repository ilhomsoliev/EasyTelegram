package com.ilhomsoliev.chat.model.message

import com.ilhomsoliev.chat.model.message.messageContent.MessageContentModel
import com.ilhomsoliev.chat.model.message.messageContent.map
import com.ilhomsoliev.core.getKoinInstance
import com.ilhomsoliev.profile.repository.ProfileRepository
import com.ilhomsoliev.profile.model.UserModel
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.MessageAnimation
import org.drinkless.tdlib.TdApi.MessageAudio
import org.drinkless.tdlib.TdApi.MessageText

data class MessageModel(
    val id: Long,
    val chatId: Long,
    val sender: UserModel?,
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
    val messageThreadId: Long,
    val viaBotUserId: Long,
    val content: MessageContentModel,
)

suspend fun TdApi.Message.map(): MessageModel {
    val userRepository: ProfileRepository = getKoinInstance()

    return MessageModel(
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
        sender = userRepository?.run {
            try {
                val sender = (senderId as TdApi.MessageSenderUser)
                this.getUserById(sender.userId)
            } catch (e: Exception) {
                null
            }

        },
        messageThreadId = messageThreadId,
        viaBotUserId = viaBotUserId,
        content = content.getMessageTypeModel(),
    )
}

// TODO add all types
fun TdApi.MessageContent.getMessageTypeModel(): MessageContentModel =
    when (this.constructor) {
        MessageText.CONSTRUCTOR -> (this as MessageText).map()
        MessageAnimation.CONSTRUCTOR -> (this as MessageAnimation).map()
        MessageAudio.CONSTRUCTOR -> (this as MessageAudio).map()
        else -> {
            MessageContentModel.NotDefined
        }
    }