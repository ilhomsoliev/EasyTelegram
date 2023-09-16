package com.ilhomsoliev.chat.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.ilhomsoliev.chat.viewmodel.ChatViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.drinkless.td.libcore.telegram.TdApi

@Composable
fun ChatScreen(
    vm: ChatViewModel,
    navController: NavController,
    chatId: Long
) {
    val scope = rememberCoroutineScope()

    val chat = vm.chat?.collectAsState(null)
    val answer by vm.answer.collectAsState()
    val messages = vm.messagesPaged?.collectAsLazyPagingItems()

    LaunchedEffect(key1 = Unit, block = {
        vm.loadChat(chatId)
        /*while (true) {
            delay(3000L)
            messages?.refresh()
        }*/
    })

    ChatContent(
        state = ChatState(
            chat = chat?.value,
            answer = answer,
            downloadManager = vm.downloadManager,
            messages = messages
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
                    messages?.refresh()
                }
            }

            override fun onBack() {
                navController.popBackStack()
            }
        })
}