package com.ilhomsoliev.home.presentation.chat_item

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
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.chat.model.chat.ChatModel
import com.ilhomsoliev.profile.model.UserModel
import com.ilhomsoliev.shared.shared.MutedIcon
import com.ilhomsoliev.shared.shared.ReadIcon
import com.ilhomsoliev.shared.shared.UnreadIcon

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
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = chatModel.title,
                maxLines = 1,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 15.sp,
                    fontWeight = FontWeight(600),
                )
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

            chatModel.lastMessage?.date?.toLong()?.let { it * 1000 }?.let {
                ChatTime(it.toRelativeTimeSpan(), modifier = Modifier.alpha(0.6f))
            }
        }
    }

}
