package com.ilhomsoliev.tgcore

import android.util.Log
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import org.drinkless.td.libcore.telegram.Client
import org.drinkless.td.libcore.telegram.TdApi

open class TelegramClient(
    val tdLibParameters: TdApi.TdlibParameters
) : Client.ResultHandler {

    private val TAG = TelegramClient::class.java.simpleName

    val baseClient: Client = Client.create(this, null, null)

    init {
        baseClient.send(TdApi.SetLogVerbosityLevel(1), this)
    }

    override fun onResult(data: TdApi.Object) {
        Log.d(TAG, "onResult: ${data::class.java.simpleName}")
        when (data.constructor) {
            TdApi.UpdateAuthorizationState.CONSTRUCTOR -> {
                Log.d(TAG, "UpdateAuthorizationState")
              //  onAuthorizationStateUpdated((data as TdApi.UpdateAuthorizationState).authorizationState)
            }

            TdApi.UpdateOption.CONSTRUCTOR -> {

            }
            else -> Log.d(TAG, "Unhandled onResult call with data: $data.")
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