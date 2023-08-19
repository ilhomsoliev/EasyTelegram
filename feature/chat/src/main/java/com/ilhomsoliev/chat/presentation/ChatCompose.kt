package com.ilhomsoliev.chat.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.chat.presentation.message_item.MessageItem
import com.ilhomsoliev.shared.TelegramImage
import com.ilhomsoliev.shared.TgDownloadManager
import com.ilhomsoliev.shared.shared.PaperclipIcon
import com.ilhomsoliev.shared.shared.SendMessageIcon
import com.ilhomsoliev.shared.shared.SmileFaceIcon
import org.drinkless.td.libcore.telegram.TdApi

data class ChatState(
    val chat: TdApi.Chat? = null,
    val answer: String,
    val downloadManager: TgDownloadManager,
    val messages: LazyPagingItems<MessageModel>?,
)

interface ChatCallback {
    fun onAnswerChange(value: String)
    fun onSendMessage()
    fun onBack()

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatContent(
    state: ChatState,
    callback: ChatCallback
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        callback.onBack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                    TelegramImage(
                        modifier = Modifier.clip(CircleShape),
                        downloadManager = state.downloadManager,
                        file = state.chat?.photo?.small
                    )
                    Text(text = state.chat?.title ?: "")
                }
                IconButton(onClick = {

                }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                }
            }

        },
        bottomBar = {
            MessageInput(
                input = state.answer,
                insertGif = {
                    // TODO
                }, attachFile = {
                    // todo
                }, sendMessage = {
                    callback.onSendMessage()
                }, onInputChange = {
                    callback.onAnswerChange(it)
                })
        }
    ) {
        state.messages?.run {
            ChatHistory(
                downloadManager = state.downloadManager,
                messages = this,
                modifier = Modifier
                    .fillMaxWidth(),
                it,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageInput(
    modifier: Modifier = Modifier,
    input: String,
    onInputChange: (String) -> Unit,
    insertGif: () -> Unit = {},
    attachFile: () -> Unit = {},
    sendMessage: () -> Unit,
) {
    Row(
        modifier = modifier
            .background(Color(0xEED9D9D9))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.padding(6.dp),
            imageVector = PaperclipIcon,
            contentDescription = null
        )

        BasicTextField(
            modifier = Modifier.weight(1f),
            value = input, onValueChange = {
                onInputChange(it)
            }, maxLines = 5, decorationBox = {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0x1F767680))
                        .padding(vertical = 6.dp, horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        it()
                    }
                    Image(
                        modifier = Modifier.clickable {
                            //TODO
                        },
                        imageVector = SmileFaceIcon,
                        contentDescription = null
                    )
                }

            })
        AnimatedVisibility(visible = input.isNotBlank()) {
            Box(
                modifier = Modifier.align(Alignment.Bottom),
                contentAlignment = Alignment.BottomCenter
            ) {
                IconButton(onClick = { sendMessage() }) {
                    Image(
                        imageVector = SendMessageIcon,
                        contentDescription = null
                    )
                }
            }
        }
        /* if (input.isEmpty()) {
                 Row {
                     IconButton(onClick = attachFile) {
                         Icon(
                             imageVector = Icons.Outlined.AttachFile,
                             contentDescription = null
                         )
                     }
                     IconButton(onClick = { }) {
                         Icon(
                             imageVector = Icons.Outlined.Mic,
                             contentDescription = null
                         )
                     }
                 }
             } else {
                 IconButton(onClick = { sendMessage() }) {
                     Icon(
                         imageVector = Icons.Outlined.Send,
                         contentDescription = null
                     )
                 }
             }*/

    }
}

@Composable
fun ChatHistory(
    downloadManager: TgDownloadManager,
    messages: LazyPagingItems<MessageModel>,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
) {
    LazyColumn(modifier = modifier, reverseLayout = true) {
        item {
            Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding()))
        }
        when {
            messages.loadState.refresh is LoadState.Loading -> {
                item {
                    CircularProgressIndicator() // TODO ChatLoading()
                }
            }

            messages.loadState.refresh is LoadState.Error -> {
                item {
                    Text(
                        text = "Cannot load messages",
                        // style = MaterialTheme.typography.h5,
                        modifier = modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    )
                }
            }

            messages.loadState.refresh is LoadState.NotLoading && messages.itemCount == 0 -> {
                item {
                    Text("Empty")
                }
            }
        }

        itemsIndexed(messages.itemSnapshotList.items) { i, message ->
            message.let {
                val userId = message.sender?.id
                val previousMessageUserId = if (i > 0) messages[i - 1]?.sender?.id else null
                MessageItem(
                    isSameUserFromPreviousMessage = userId == previousMessageUserId,
                    downloadManager = downloadManager,
                    message = it
                )
            }

        }

    }
}
