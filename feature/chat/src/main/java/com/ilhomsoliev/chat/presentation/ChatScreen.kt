package com.ilhomsoliev.chat.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.ilhomsoliev.chat.model.message.map
import com.ilhomsoliev.chat.viewmodel.ChatViewModel
import com.ilhomsoliev.tgcore.newMessageArrivedFromTdApi
import kotlinx.coroutines.launch
import org.drinkless.tdlib.TdApi

@Composable
fun ChatScreen(
    vm: ChatViewModel,
    navController: NavController,
    chatId: Long
) {
    val scope = rememberCoroutineScope()

    val chat = vm.chat.collectAsState()
    val answer by vm.answer.collectAsState()
    val messages by vm.messages.collectAsState()
    val isLoadingNewMessages by vm.messagesFetchingRunning.collectAsState()

    var isCleanChatHistoryDialogActive by remember { mutableStateOf(false) }
    var isDeleteChatHistoryDialogActive by remember { mutableStateOf(false) }
    var isPinMessageDialogActive by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit, block = {
        vm.loadChat(chatId)
        vm.onMessageItemPass(-1)
    })

    LaunchedEffect(key1 = newMessageArrivedFromTdApi.value, block = {
        newMessageArrivedFromTdApi.value?.map()?.let { newMessage ->
            vm.onMessageArrived(newMessage)
            newMessageArrivedFromTdApi.value = null
        }
    })

    ChatContent(
        state = ChatState(
            chat = chat.value,
            answer = answer,
            downloadManager = vm.downloadManager,
            messages = messages,
            isLoadingNewMessages = isLoadingNewMessages ?: false,
            isCleanChatHistoryDialogActive = isCleanChatHistoryDialogActive,
            isDeleteChatHistoryDialogActive = isDeleteChatHistoryDialogActive,
            isPinMessageDialogActive = isPinMessageDialogActive,
        ),
        callback = object : ChatCallback {
            override fun onAnswerChange(value: String) {
                scope.launch {
                    vm.changeAnswer(value)
                }
            }

            override fun onSendMessage() {
                scope.launch {
                    vm.sendMessage(
                        chatId = chatId,
                        inputMessageContent = TdApi.InputMessageText(
                            TdApi.FormattedText(
                                answer,
                                emptyArray()
                            ), false, false
                        )
                    )
                }
            }

            override fun onBack() {
                navController.popBackStack()
            }

            override fun onItemPass(index: Int) {
                scope.launch {
                    vm.onMessageItemPass(index)
                }
            }

            override fun onSearchIconClick() {
                TODO("Not yet implemented")
            }

            override fun onIsCleanChatHistoryDialogActiveChange(value: Boolean) {
                isCleanChatHistoryDialogActive = value
            }

            override fun onIsDeleteChatHistoryDialogActiveChange(value: Boolean) {
                isDeleteChatHistoryDialogActive = value
            }

            override fun onIsPinMessageDialogActiveChange(value: Boolean) {
                isPinMessageDialogActive = value
            }
        })
}