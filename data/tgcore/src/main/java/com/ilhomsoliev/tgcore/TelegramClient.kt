package com.ilhomsoliev.tgcore

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.drinkless.tdlib.Client
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.ChatPosition
import org.drinkless.tdlib.TdApi.Function
import org.drinkless.tdlib.TdApi.GetChat
import org.drinkless.tdlib.TdApi.UpdateChatPosition
import org.drinkless.tdlib.TdApi.UpdateNewChat
import org.drinkless.tdlib.TdApi.UpdateUserStatus


class TelegramClient(
    private val tdLibParameters: TdLibParameters
) : Client.ResultHandler {

    private val TAG = TelegramClient::class.java.simpleName

    val baseClient: Client = Client.create(this@TelegramClient, null, null)

    val _authState = MutableStateFlow<Authentication>(Authentication.UNKNOWN)

    private val requestScope = CoroutineScope(Dispatchers.IO)
    fun setAuth(auth: Authentication) {
        _authState.value = auth
    }

    private fun doAsync(job: () -> Unit) {
        requestScope.launch { job() }
    }

    init {
        Log.d("TelegramClient", "onInit")
        baseClient.send(TdApi.SetLogVerbosityLevel(1), this)

        doAsync {
            val request = TdApi.SetTdlibParameters()
            request.databaseDirectory = tdLibParameters.databaseDirectory
            request.useMessageDatabase = tdLibParameters.useMessageDatabase
            request.useSecretChats = tdLibParameters.useSecretChats
            request.apiId = tdLibParameters.apiId
            request.apiHash = tdLibParameters.apiHash
            request.systemLanguageCode = tdLibParameters.systemLanguageCode
            request.deviceModel = tdLibParameters.deviceModel
            request.applicationVersion = tdLibParameters.applicationVersion
            request.enableStorageOptimizer = tdLibParameters.enableStorageOptimizer
            baseClient.send(request) {
                when (it.constructor) {
                    TdApi.Ok.CONSTRUCTOR -> {}
                    TdApi.Error.CONSTRUCTOR -> {}
                }
            }
        }
    }

    override fun onResult(data: TdApi.Object) {
        fun updateChatPosition(position: ChatPosition, chatId: Long) {
            if (position.list.constructor != TdApi.ChatListMain.CONSTRUCTOR) {
                return
            }

            val chat: TdApi.Chat? = AppDataState.getChat(chatId)

            chat?.let {
                synchronized(chat) {
                    var i = 0
                    while (i < chat.positions.size) {
                        if (chat.positions[i].list.constructor == TdApi.ChatListMain.CONSTRUCTOR) {
                            break
                        }
                        i++
                    }
                    val new_positions =
                        arrayOfNulls<ChatPosition>(chat.positions.size + (if (position.order == 0L) 0 else 1) - if (i < chat.positions.size) 1 else 0)
                    var pos = 0
                    if (position.order != 0L) {
                        new_positions[pos++] = position
                    }
                    for (j in chat.positions.indices) {
                        if (j != i) {
                            new_positions[pos++] = chat.positions[j]
                        }
                    }
                    assert(pos == new_positions.size)
                    setChatPositions(chat, new_positions)
                    // newUpdateFromTdApi.value = !newUpdateFromTdApi.value
                }
            }
        }
        Log.d("Hello onResult Updates", data.javaClass.name.toString())
        when (data.constructor) {
            TdApi.UpdateAuthorizationState.CONSTRUCTOR -> {
                onAuthorizationStateUpdated((data as TdApi.UpdateAuthorizationState).authorizationState)
            }

            TdApi.UpdateUser.CONSTRUCTOR -> {
                val updateUser = data as TdApi.UpdateUser
                UpdateHandler.onUpdateUser(updateUser)
            }

            TdApi.UpdateUserStatus.CONSTRUCTOR -> {
                val updateUserStatus = data as UpdateUserStatus
                UpdateHandler.onUpdateUserStatus(updateUserStatus)
            }

            TdApi.UpdateBasicGroup.CONSTRUCTOR -> {}
            TdApi.UpdateSupergroup.CONSTRUCTOR -> {}
            TdApi.UpdateSecretChat.CONSTRUCTOR -> {}

            UpdateNewChat.CONSTRUCTOR -> {
                val updateNewChat = data as UpdateNewChat
                val chat = updateNewChat.chat
                synchronized(chat) {
                    AppDataState.putChat(chat.id, chat)
                    val positions = chat.positions
                    chat.positions = arrayOfNulls(0) //  TODO
                    setChatPositions(chat, positions)
                    newUpdateFromTdApi.value = !newUpdateFromTdApi.value
                }
            }

            TdApi.UpdateChatTitle.CONSTRUCTOR -> {}
            TdApi.UpdateChatPhoto.CONSTRUCTOR -> {}
            TdApi.UpdateChatPermissions.CONSTRUCTOR -> {}
            TdApi.UpdateChatLastMessage.CONSTRUCTOR -> {
                val newMessage = data as TdApi.UpdateChatLastMessage

                val chat = AppDataState.getChat(newMessage.chatId)
                chat?.let {
                    synchronized(it) {
                        chat.lastMessage = newMessage.lastMessage;
                        setChatPositions(chat, newMessage.positions);
                    }
                }
                // TODO
                //UpdateHandler.onUpdateChatLastMessage(newMessage)

            }

            UpdateChatPosition.CONSTRUCTOR -> run {
                val updateChatPosition = (data as UpdateChatPosition)
                updateChatPosition(updateChatPosition.position, chatId = updateChatPosition.chatId)
            }

            TdApi.UpdateChatReadInbox.CONSTRUCTOR -> {}
            TdApi.UpdateChatReadOutbox.CONSTRUCTOR -> {}
            TdApi.UpdateChatActionBar.CONSTRUCTOR -> {}
            TdApi.UpdateChatAvailableReactions.CONSTRUCTOR -> {}
            TdApi.UpdateChatDraftMessage.CONSTRUCTOR -> {}
            TdApi.UpdateChatMessageSender.CONSTRUCTOR -> {}
            TdApi.UpdateChatMessageAutoDeleteTime.CONSTRUCTOR -> {}
            TdApi.UpdateChatNotificationSettings.CONSTRUCTOR -> {}
            TdApi.UpdateChatPendingJoinRequests.CONSTRUCTOR -> {}
            TdApi.UpdateChatReplyMarkup.CONSTRUCTOR -> {}
            TdApi.UpdateChatBackground.CONSTRUCTOR -> {}
            TdApi.UpdateChatTheme.CONSTRUCTOR -> {}
            TdApi.UpdateChatUnreadMentionCount.CONSTRUCTOR -> {}
            TdApi.UpdateChatUnreadReactionCount.CONSTRUCTOR -> {}
            TdApi.UpdateChatVideoChat.CONSTRUCTOR -> {}
            TdApi.UpdateChatDefaultDisableNotification.CONSTRUCTOR -> {}
            TdApi.UpdateChatHasProtectedContent.CONSTRUCTOR -> {}
            TdApi.UpdateChatIsTranslatable.CONSTRUCTOR -> {}
            TdApi.UpdateChatIsMarkedAsUnread.CONSTRUCTOR -> {}
            TdApi.UpdateChatBlockList.CONSTRUCTOR -> {}
            TdApi.UpdateChatHasScheduledMessages.CONSTRUCTOR -> {}
            TdApi.UpdateMessageMentionRead.CONSTRUCTOR -> {}
            TdApi.UpdateMessageUnreadReactions.CONSTRUCTOR -> {}
            TdApi.UpdateUserFullInfo.CONSTRUCTOR -> {}
            TdApi.UpdateBasicGroupFullInfo.CONSTRUCTOR -> {}
            TdApi.UpdateSupergroupFullInfo.CONSTRUCTOR -> {}
            TdApi.UpdateOption.CONSTRUCTOR -> {}
            TdApi.UpdateNewMessage.CONSTRUCTOR -> {

                val updateNewMessage = (data as TdApi.UpdateNewMessage)
                val message = updateNewMessage.message
                requestScope.launch {
                    val chat = baseClient.send(GetChat(message.chatId)).first()
                    Log.d("Hello client chat", chat.toString())
                    synchronized(chat) {
                        AppDataState.putChat(chat.id, chat)
                        /*for (position in chat.positions) {
                            updateChatPosition(
                                chatId = chat.id,
                                position = position
                            )
                        }*/
                        val positions = chat.positions
                        // chat.positions = arrayOfNulls(0) // TODO wtf

                        setChatPositions(chat, positions)
                    }
                    Log.d("Hello update new message", message.toString())
                    /*   synchronized(message) {
                           newUpdateFromTdApi.value = !newUpdateFromTdApi.value
                           newMessageArrivedFromTdApi.value = message
                       }*/
                }
            }

            TdApi.UpdateFile.CONSTRUCTOR -> {
                val updateFile = (data as TdApi.UpdateFile)
                UpdateHandler.onUpdateFile(updateFile)
            }

            else -> Log.d(TAG, "Unhandled onResult call with data: $data.")
        }
    }

    private fun setChatPositions(chat: TdApi.Chat, positions: Array<ChatPosition?>) {
        synchronized(AppDataState.mainChatList) {
            synchronized(chat) {
                for (position in chat.positions) {
                    if (position.list.constructor == TdApi.ChatListMain.CONSTRUCTOR) {
                        val isRemoved: Boolean =
                            AppDataState.mainChatList.remove(
                                AppDataState.OrderedChat(
                                    chat.id,
                                    position
                                )
                            )
                        Log.d("Hello isRemoved", isRemoved.toString())
                        assert(isRemoved) // TODO Here we have a problem
                    }
                }
                chat.positions = positions
                for (position in chat.positions) {
                    if (position.list.constructor == TdApi.ChatListMain.CONSTRUCTOR) {
                        val isAdded: Boolean = AppDataState.mainChatList.add(
                            AppDataState.OrderedChat(
                                chat.id,
                                position
                            )
                        )
                        assert(isAdded)
                    }
                }
                newUpdateFromTdApi.value = !newUpdateFromTdApi.value
            }
        }
    }


    private fun onAuthorizationStateUpdated(authorizationState: TdApi.AuthorizationState) {
        when (authorizationState.constructor) {
            TdApi.AuthorizationStateWaitTdlibParameters.CONSTRUCTOR -> {
                Log.d(
                    TAG,
                    "onResult: AuthorizationStateWaitTdlibParameters -> state = UNAUTHENTICATED"
                )
                setAuth(Authentication.UNAUTHENTICATED)
            }

            TdApi.AuthorizationStateWaitPhoneNumber.CONSTRUCTOR -> {
                Log.d(TAG, "onResult: AuthorizationStateWaitPhoneNumber -> state = WAIT_FOR_NUMBER")
                setAuth(Authentication.WAIT_FOR_NUMBER)
            }

            TdApi.AuthorizationStateWaitCode.CONSTRUCTOR -> {
                val response = (authorizationState as TdApi.AuthorizationStateWaitCode).codeInfo
                Log.d(
                    TAG,
                    "onResult: AuthorizationStateWaitCode -> state = WAIT_FOR_CODE $response"
                )
                setAuth(
                    Authentication.WAIT_FOR_CODE(
                        phoneNumber = response.phoneNumber,
                        type = response.type,
                        nextType = response.nextType,
                        timeout = response.timeout,
                    )
                )
            }

            TdApi.AuthorizationStateWaitPassword.CONSTRUCTOR -> {
                val response = (authorizationState as TdApi.AuthorizationStateWaitPassword)
                Log.d(TAG, "onResult: AuthorizationStateWaitPassword")
                setAuth(
                    Authentication.WAIT_FOR_PASSWORD(
                        passwordHint = response.passwordHint,
                        hasRecoveryEmailAddress = response.hasRecoveryEmailAddress,
                        hasPassportData = response.hasPassportData,
                        recoveryEmailAddressPattern = response.recoveryEmailAddressPattern,
                    )
                )
            }

            TdApi.AuthorizationStateReady.CONSTRUCTOR -> {
                Log.d(TAG, "onResult: AuthorizationStateReady -> state = AUTHENTICATED")
                setAuth(Authentication.AUTHENTICATED)
            }

            TdApi.AuthorizationStateLoggingOut.CONSTRUCTOR -> {
                Log.d(TAG, "onResult: AuthorizationStateLoggingOut")
                setAuth(Authentication.UNAUTHENTICATED)
            }

            TdApi.AuthorizationStateClosing.CONSTRUCTOR -> {
                Log.d(TAG, "onResult: AuthorizationStateClosing")
            }

            TdApi.AuthorizationStateClosed.CONSTRUCTOR -> {
                Log.d(TAG, "onResult: AuthorizationStateClosed")
            }

            else -> Log.d(TAG, "Unhandled authorizationState with data: $authorizationState.")
        }
    }
}

fun <T : TdApi.Object> TelegramClient.sendAsFlow(query: Function<T>): Flow<TdApi.Object> =
    callbackFlow {
        baseClient.send(query) {
            when (it.constructor) {
                TdApi.Error.CONSTRUCTOR -> {
                    error("")
                }

                else -> {
                    trySend(it).isSuccess
                }
            }
            //close()
        }
        awaitClose { }
    }

inline fun <reified T : TdApi.Object> TelegramClient.send(query: Function<T>): Flow<T> =
    sendAsFlow(query).map { it as T }

fun <T : TdApi.Object> Client.sendAsFlow(query: Function<T>): Flow<TdApi.Object> =
    callbackFlow {
        this@sendAsFlow.send(query) {
            when (it.constructor) {
                TdApi.Error.CONSTRUCTOR -> {
                    error("")
                }

                else -> {
                    trySend(it).isSuccess
                }
            }
            //close()
        }
        awaitClose { }
    }

inline fun <reified T : TdApi.Object> Client.send(query: Function<T>): Flow<T> =
    sendAsFlow(query).map { it as T }

data class TdLibParameters(
    val databaseDirectory: String,
    val useMessageDatabase: Boolean,
    val useSecretChats: Boolean,
    val apiId: Int,
    val apiHash: String,
    val systemLanguageCode: String,
    val deviceModel: String,
    val applicationVersion: String,
    val enableStorageOptimizer: Boolean,
)