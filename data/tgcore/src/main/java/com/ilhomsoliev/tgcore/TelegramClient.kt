package com.ilhomsoliev.tgcore

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.drinkless.tdlib.Client
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.ChatPosition
import org.drinkless.tdlib.TdApi.Function
import org.drinkless.tdlib.TdApi.UpdateChatPosition
import org.drinkless.tdlib.TdApi.UpdateUserStatus



class TelegramClient(
    private val tdLibParameters: TdLibParameters
) : Client.ResultHandler {

    private val TAG = TelegramClient::class.java.simpleName

    val baseClient: Client = Client.create(this@TelegramClient, null, null)

    val _authState = MutableStateFlow(Authentication.UNKNOWN)

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
            val request = TdApi.SetTdlibParameters();
            request.databaseDirectory = tdLibParameters.databaseDirectory
            request.useMessageDatabase = tdLibParameters.useMessageDatabase
            request.useSecretChats = tdLibParameters.useSecretChats
            request.apiId = tdLibParameters.apiId
            request.apiHash = tdLibParameters.apiHash
            request.systemLanguageCode = tdLibParameters.systemLanguageCode
            request.deviceModel = tdLibParameters.deviceModel
            request.applicationVersion = tdLibParameters.applicationVersion
            request.enableStorageOptimizer = tdLibParameters.enableStorageOptimizer

            baseClient.send(
                request
            ) {
                Log.d(TAG, "SetTdlibParameters result: $it")
                when (it.constructor) {
                    TdApi.Ok.CONSTRUCTOR -> {
                        //result.postValue(true)
                    }

                    TdApi.Error.CONSTRUCTOR -> {
                        //result.postValue(false)
                    }
                }
            }
        }
    }

    override fun onResult(data: TdApi.Object) {
        Log.d(TAG, "onResult: ${data::class.java.simpleName}")

        // newTdApiUpdates.value = data

        when (data.constructor) {
            TdApi.UpdateAuthorizationState.CONSTRUCTOR -> {
                Log.d(TAG, "UpdateAuthorizationState")
                onAuthorizationStateUpdated((data as TdApi.UpdateAuthorizationState).authorizationState)
            }

            TdApi.UpdateUser.CONSTRUCTOR -> {
                val updateUser = data as TdApi.UpdateUser
                AppDataState.putUser(updateUser.user.id, updateUser.user)
            }

            TdApi.UpdateUserStatus.CONSTRUCTOR -> {
                val updateUserStatus = data as UpdateUserStatus
                val user: TdApi.User? = AppDataState.users.get(updateUserStatus.userId)
                user?.let { synchronized(it) { user.status = updateUserStatus.status } }
            }

            TdApi.UpdateBasicGroup.CONSTRUCTOR -> {

            }

            TdApi.UpdateSupergroup.CONSTRUCTOR -> {

            }

            TdApi.UpdateSecretChat.CONSTRUCTOR -> {

            }

            TdApi.UpdateNewChat.CONSTRUCTOR -> {

            }

            TdApi.UpdateChatTitle.CONSTRUCTOR -> {

            }

            TdApi.UpdateChatPhoto.CONSTRUCTOR -> {

            }

            TdApi.UpdateChatPermissions.CONSTRUCTOR -> {

            }

            TdApi.UpdateChatLastMessage.CONSTRUCTOR -> {

            }

            TdApi.UpdateChatPosition.CONSTRUCTOR -> run {
                val updateChat = data as UpdateChatPosition
                if (updateChat.position.list.constructor != TdApi.ChatListMain.CONSTRUCTOR) {
                    return@run
                }

                val chat: TdApi.Chat = AppDataState.chats.get(updateChat.chatId)!! // TODO
                synchronized(chat) {
                    var i: Int
                    i = 0
                    while (i < chat.positions.size) {
                        if (chat.positions[i].list.constructor == TdApi.ChatListMain.CONSTRUCTOR) {
                            break
                        }
                        i++
                    }
                    val new_positions =
                        arrayOfNulls<ChatPosition>(chat.positions.size + (if (updateChat.position.order == 0L) 0 else 1) - if (i < chat.positions.size) 1 else 0)
                    var pos = 0
                    if (updateChat.position.order != 0L) {
                        new_positions[pos++] = updateChat.position
                    }
                    for (j in chat.positions.indices) {
                        if (j != i) {
                            new_positions[pos++] = chat.positions[j]
                        }
                    }
                    assert(pos == new_positions.size)
                    setChatPositions(chat, new_positions)
                }
            }

            TdApi.UpdateChatReadInbox.CONSTRUCTOR -> {

            }

            TdApi.UpdateChatReadOutbox.CONSTRUCTOR -> {

            }

            TdApi.UpdateChatActionBar.CONSTRUCTOR -> {

            }

            TdApi.UpdateChatAvailableReactions.CONSTRUCTOR -> {

            }

            TdApi.UpdateChatDraftMessage.CONSTRUCTOR -> {

            }

            TdApi.UpdateChatMessageSender.CONSTRUCTOR -> {

            }

            TdApi.UpdateChatMessageAutoDeleteTime.CONSTRUCTOR -> {

            }

            TdApi.UpdateChatNotificationSettings.CONSTRUCTOR -> {

            }

            TdApi.UpdateChatPendingJoinRequests.CONSTRUCTOR -> {

            }

            TdApi.UpdateChatReplyMarkup.CONSTRUCTOR -> {

            }

            TdApi.UpdateChatBackground.CONSTRUCTOR -> {

            }

            TdApi.UpdateChatTheme.CONSTRUCTOR -> {

            }

            TdApi.UpdateChatUnreadMentionCount.CONSTRUCTOR -> {

            }

            TdApi.UpdateChatUnreadReactionCount.CONSTRUCTOR -> {

            }

            TdApi.UpdateChatVideoChat.CONSTRUCTOR -> {

            }

            TdApi.UpdateChatDefaultDisableNotification.CONSTRUCTOR -> {

            }

            TdApi.UpdateChatHasProtectedContent.CONSTRUCTOR -> {

            }

            TdApi.UpdateChatIsTranslatable.CONSTRUCTOR -> {}
            TdApi.UpdateChatIsMarkedAsUnread.CONSTRUCTOR -> {}
            TdApi.UpdateChatBlockList.CONSTRUCTOR -> {}
            TdApi.UpdateChatHasScheduledMessages.CONSTRUCTOR -> {}
            TdApi.UpdateMessageMentionRead.CONSTRUCTOR -> {}
            TdApi.UpdateMessageUnreadReactions.CONSTRUCTOR -> {}
            TdApi.UpdateUserFullInfo.CONSTRUCTOR -> {}
            TdApi.UpdateBasicGroupFullInfo.CONSTRUCTOR -> {}
            TdApi.UpdateSupergroupFullInfo.CONSTRUCTOR -> {}

            TdApi.UpdateOption.CONSTRUCTOR -> {

            }

            else -> Log.d(TAG, "Unhandled onResult call with data: $data.")
        }
    }

    private fun setChatPositions(chat: TdApi.Chat, positions: Array<ChatPosition?>) {
        val mainChatList = AppDataState.mainChatList

        synchronized(mainChatList) {
            synchronized(chat) {
                for (position in chat.positions) {
                    if (position.list.constructor == TdApi.ChatListMain.CONSTRUCTOR) {
                        val isRemoved: Boolean =
                            mainChatList.remove(AppDataState.OrderedChat(chat.id, position))
                        assert(isRemoved)
                    }
                }
                chat.positions = positions
                for (position in chat.positions) {
                    if (position.list.constructor == TdApi.ChatListMain.CONSTRUCTOR) {
                        val isAdded: Boolean = mainChatList.add(
                            AppDataState.OrderedChat(
                                chat.id,
                                position
                            )
                        )
                        assert(isAdded)
                    }
                }
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

            /*
                        TdApi.AuthorizationStateWaitEncryptionKey.CONSTRUCTOR -> {
                            Log.d(TAG, "onResult: AuthorizationStateWaitEncryptionKey")
                            baseClient.send(TdApi.CheckDatabaseEncryptionKey()) {
                                when (it.constructor) {
                                    TdApi.Ok.CONSTRUCTOR -> {
                                        Log.d(TAG, "CheckDatabaseEncryptionKey: OK")
                                    }

                                    TdApi.Error.CONSTRUCTOR -> {
                                        Log.d(TAG, "CheckDatabaseEncryptionKey: Error")
                                    }
                                }
                            }
                        }
            */

            TdApi.AuthorizationStateWaitPhoneNumber.CONSTRUCTOR -> {
                Log.d(TAG, "onResult: AuthorizationStateWaitPhoneNumber -> state = WAIT_FOR_NUMBER")
                setAuth(Authentication.WAIT_FOR_NUMBER)
            }

            TdApi.AuthorizationStateWaitCode.CONSTRUCTOR -> {
                Log.d(TAG, "onResult: AuthorizationStateWaitCode -> state = WAIT_FOR_CODE")
                setAuth(Authentication.WAIT_FOR_CODE)
            }

            TdApi.AuthorizationStateWaitPassword.CONSTRUCTOR -> {
                Log.d(TAG, "onResult: AuthorizationStateWaitPassword")
                setAuth(Authentication.WAIT_FOR_PASSWORD)
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