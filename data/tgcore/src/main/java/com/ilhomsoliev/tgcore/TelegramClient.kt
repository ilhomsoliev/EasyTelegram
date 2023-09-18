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
import org.drinkless.td.libcore.telegram.Client
import org.drinkless.td.libcore.telegram.TdApi

val newTdApiUpdates = mutableStateOf<TdApi.Object?>(null)


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
            TdApi.SetTdlibParameters()
            baseClient.send(
                TdApi.SetTdlibParameters(
                    useTestDc = tdLibParameters.useTestDc,
                    databaseDirectory = tdLibParameters.databaseDirectory,
                    filesDirectory = tdLibParameters.filesDirectory,
                    databaseEncryptionKey = tdLibParameters.databaseEncryptionKey,
                    useFileDatabase = tdLibParameters.useFileDatabase,
                    useChatInfoDatabase = tdLibParameters.useChatInfoDatabase,
                    useMessageDatabase = tdLibParameters.useMessageDatabase,
                    useSecretChats = tdLibParameters.useSecretChats,
                    apiId = tdLibParameters.apiId,
                    apiHash = tdLibParameters.apiHash,
                    systemLanguageCode = tdLibParameters.systemLanguageCode,
                    deviceModel = tdLibParameters.deviceModel,
                    systemVersion = tdLibParameters.systemVersion,
                    applicationVersion = tdLibParameters.applicationVersion,
                    enableStorageOptimizer = tdLibParameters.enableStorageOptimizer,
                    ignoreFileNames = tdLibParameters.ignoreFileNames,
                    /*
            apiId = tdLibParameters.apiId,
            apiHash = tdLibParameters.apiHash,
            useMessageDatabase = tdLibParameters.useMessageDatabase,
            useSecretChats = tdLibParameters.useSecretChats,
            systemLanguageCode = tdLibParameters.systemLanguageCode,
            databaseDirectory = tdLibParameters.databaseDirectory,
            deviceModel = tdLibParameters.deviceModel,
            systemVersion = tdLibParameters.systemVersion,
            applicationVersion = tdLibParameters.applicationVersion,
            enableStorageOptimizer = tdLibParameters.enableStorageOptimizer,
            useTestDc = false,
            databaseEncryptionKey = tdLibParameters.databaseEncryptionKey,
            useFileDatabase = false,
            useChatInfoDatabase = false,
            ignoreFileNames = false,*/
                )
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

        newTdApiUpdates.value = data

        when (data.constructor) {
            TdApi.UpdateAuthorizationState.CONSTRUCTOR -> {
                Log.d(TAG, "UpdateAuthorizationState")
                onAuthorizationStateUpdated((data as TdApi.UpdateAuthorizationState).authorizationState)
            }

            TdApi.UpdateOption.CONSTRUCTOR -> {

            }

            else -> Log.d(TAG, "Unhandled onResult call with data: $data.")
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

fun TelegramClient.sendAsFlow(query: TdApi.Function): Flow<TdApi.Object> = callbackFlow {
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

inline fun <reified T : TdApi.Object> TelegramClient.send(query: TdApi.Function): Flow<T> =
    sendAsFlow(query).map { it as T }

data class TdLibParameters(
    val useTestDc: Boolean,
    val databaseDirectory: String,
    val filesDirectory: String,
    val databaseEncryptionKey: Array<Byte>,
    val useFileDatabase: Boolean,
    val useChatInfoDatabase: Boolean,
    val useMessageDatabase: Boolean,
    val useSecretChats: Boolean,
    val apiId: Int,
    val apiHash: String,
    val systemLanguageCode: String,
    val deviceModel: String,
    val systemVersion: String,
    val applicationVersion: String,
    val enableStorageOptimizer: Boolean,
    val ignoreFileNames: Boolean,
)