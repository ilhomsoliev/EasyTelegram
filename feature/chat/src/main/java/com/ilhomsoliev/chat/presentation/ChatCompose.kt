package com.ilhomsoliev.chat.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.chat.R
import com.ilhomsoliev.chat.model.chat.ChatModel
import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.chat.presentation.message_item.MessageItem
import com.ilhomsoliev.shared.TgDownloadManager
import com.ilhomsoliev.shared.common.ChatItemImage
import com.ilhomsoliev.shared.common.dropdown_menu.Chat3DotsDropdownMenu
import com.ilhomsoliev.shared.common.extensions.LocalDate
import com.ilhomsoliev.shared.common.extensions.getChatDateSeparator
import com.ilhomsoliev.shared.shared.dialogs.CleanChatHistoryDialog
import com.ilhomsoliev.shared.shared.icons.PaperclipIcon
import com.ilhomsoliev.shared.shared.icons.SendMessageIcon
import com.ilhomsoliev.shared.shared.icons.SmileFaceIcon

data class ChatState(
    val chat: ChatModel? = null,
    val answer: String,
    val downloadManager: TgDownloadManager,
    val messages: List<MessageModel>,
    val isLoadingNewMessages: Boolean,
    val isCleanChatHistoryDialogActive: Boolean,
    val isDeleteChatHistoryDialogActive: Boolean,
    val isPinMessageDialogActive: Boolean,
)

interface ChatCallback {
    fun onAnswerChange(value: String)
    fun onSendMessage()
    fun onBack()
    fun onItemPass(index: Int)
    fun onSearchIconClick()
    fun onIsCleanChatHistoryDialogActiveChange(value: Boolean)
    fun onIsDeleteChatHistoryDialogActiveChange(value: Boolean)
    fun onIsPinMessageDialogActiveChange(value: Boolean)
}

val chatElementsBackground = Color(0xFED9D9D9)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatContent(
    state: ChatState,
    callback: ChatCallback
) {
    Scaffold(
        topBar = {
            ChatTopAppBar(
                state = state,
                callback = callback
            )
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.chat_background),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            ChatHistory(
                modifier = Modifier
                    .fillMaxWidth(),
                state = state,
                callback = callback,
            )
        }
    }
    Dialogs(
        state = state,
        callback = callback
    )
}

@Composable
private fun Dialogs(
    state: ChatState,
    callback: ChatCallback,
) {
    CleanChatHistoryDialog(
        isDialogShown = state.isCleanChatHistoryDialogActive,
        onDismissRequest = {
            callback.onIsCleanChatHistoryDialogActiveChange(false)
        },
        onPositiveButtonClick = {

        }
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ChatTopAppBar(
    state: ChatState,
    callback: ChatCallback,
) {
    val title = state.chat?.title ?: ""
    val isMenuOpen = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(chatElementsBackground),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .weight(1f, false)
                .padding(end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                callback.onBack()
            }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
            Column(modifier = Modifier.offset((-4).dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.basicMarquee(),
                        text = title,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF232323),
                        ),
                        maxLines = 1,

                        )
                    // TODO if notifcation is enabled
                }

                // TODO
                Text(
                    text = "last seen", style = TextStyle(
                        fontSize = 11.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF979797),
                    )
                )
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            ChatItemImage(
                modifier = Modifier,
                downloadManager = state.downloadManager,
                file = state.chat?.photo?.small,
                username = title,
                imageSize = 32.dp,
            )

            IconButton(onClick = {
                isMenuOpen.value = true
            }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
            }
            Chat3DotsDropdownMenu(
                isMenuOpen = isMenuOpen.value,
                onIsMenuOpenChange = {
                    isMenuOpen.value = false
                },
                onCleanHistoryClick = {
                    callback.onIsCleanChatHistoryDialogActiveChange(true)
                },
                onSearchClick = {
                    callback.onSearchIconClick()
                },
                onDeleteChatClick = {
                    callback.onIsDeleteChatHistoryDialogActiveChange(true)
                },
            )
        }
    }
}

@Composable
private fun MessageInput(
    modifier: Modifier = Modifier,
    input: String,
    onInputChange: (String) -> Unit,
    insertGif: () -> Unit = {},
    attachFile: () -> Unit = {},
    sendMessage: () -> Unit,
) {
    Row(
        modifier = modifier
            .background(chatElementsBackground)
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
    }
}

@Composable
fun ChatHistory(
    modifier: Modifier = Modifier,
    state: ChatState,
    callback: ChatCallback,
) {
    val messages = state.messages

    LazyColumn(modifier = modifier, reverseLayout = true) {
        itemsIndexed(
            items = messages,
            key = { index, item ->
                index//item.id
            },
        ) { index, message ->

            LaunchedEffect(key1 = Unit, block = {
                callback.onItemPass(index)
            })

            message.let {
                // Message
                val userId = message.sender?.id
                val previousMessageUserId = if (index > 0) messages[index - 1].sender?.id else null

                val nextMessage = messages.getOrNull(index + 1)?.sender?.id
                val previousMessage = messages.getOrNull(index - 1)?.sender?.id
                val isLastMessage = userId != nextMessage
                val isFirstMessage = userId != previousMessage

                MessageItem(
                    isSameUserFromPreviousMessage = userId == previousMessageUserId,
                    isLastMessage = isLastMessage,
                    isFirstMessage = isFirstMessage,
                    downloadManager = state.downloadManager,
                    message = it
                )
                // Date Separator
                var dateSeparator: String? = null
                if (messages.getOrNull(index + 1) != null &&
                    LocalDate(message.date.toLong() * 1000).format("YYYY-MM-dd") != LocalDate(
                        messages[index + 1].date.toLong() * 1000
                    ).format(
                        "YYYY-MM-dd"
                    )
                ) {
                    dateSeparator = getChatDateSeparator(message.date.toLong() * 1000)
                } else if (index + 1 == messages.size) {
                    dateSeparator = getChatDateSeparator(message.date.toLong() * 1000)
                }

                dateSeparator?.let {
                    ChatDateSeparator(text = it)
                }
            }
        }
        if (state.isLoadingNewMessages) {
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier)
                }
            }
        }
    }
}

@Composable
private fun ChatDateSeparator(
    text: String,
) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFEFEFEF))
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 2.dp, horizontal = 8.dp),
                text = text,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 11.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0x99000000),
                )
            )
        }
    }
}