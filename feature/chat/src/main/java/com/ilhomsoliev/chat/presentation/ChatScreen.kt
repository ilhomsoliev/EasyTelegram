package com.ilhomsoliev.chat.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.ilhomsoliev.chat.viewmodel.ChatViewModel
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

    LaunchedEffect(key1 = Unit, block = {
        vm.loadChat(chatId)
    })

    LaunchedEffect(key1 = chat.value?.id, block = {
        if (chat.value?.id != null)
            vm.onMessageItemPass(-1)
    })

    ChatContent(
        state = ChatState(
            chat = chat.value,
            answer = answer,
            downloadManager = vm.downloadManager,
            messages = messages,
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
                    ).await()
                    vm.clearAnswer()
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
        })
}