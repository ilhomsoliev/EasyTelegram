package com.ilhomsoliev.home.presentation.chats.chat_item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.chat.model.chat.ChatModel
import com.ilhomsoliev.profile.model.UserModel
import com.ilhomsoliev.shared.shared.icons.MutedIcon
import com.ilhomsoliev.shared.shared.icons.ReadIcon
import com.ilhomsoliev.shared.shared.icons.UnreadIcon
import com.ilhomsoliev.shared.shared.utils.timeAgoOrFormattedDate

@Composable
fun ChatTitle(
    modifier: Modifier = Modifier,
    chatModel: ChatModel,
    currentUser: UserModel,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f, false),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = chatModel.title,
                maxLines = 1,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 15.sp,
                    fontWeight = FontWeight(600),
                ),
                overflow = TextOverflow.Ellipsis
            )
            if (chatModel.defaultDisableNotification) { // TODO
                Image(imageVector = MutedIcon, contentDescription = null)
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (chatModel.lastMessage?.sender?.id == currentUser.id) {
                if (chatModel.lastReadOutboxMessageId == chatModel.lastMessage?.id) {
                    Image(imageVector = ReadIcon, contentDescription = null)
                } else {
                    Image(imageVector = UnreadIcon, contentDescription = null)
                }
            }

            chatModel.lastMessage?.date?.let {
                ChatLastTimeIndicator(timeAgoOrFormattedDate(it), modifier = Modifier.alpha(0.6f))
            }
        }
    }

}
