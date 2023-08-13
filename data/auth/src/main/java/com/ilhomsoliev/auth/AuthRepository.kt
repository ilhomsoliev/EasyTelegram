package com.ilhomsoliev.auth

import android.util.Log
import com.ilhomsoliev.tgcore.Authentication
import com.ilhomsoliev.tgcore.TelegramClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.drinkless.td.libcore.telegram.TdApi

class AuthRepository(
    tdLibParameters: TdApi.TdlibParameters
) : TelegramClient(tdLibParameters) {

    private val TAG = AuthRepository::class.java.simpleName

    val authState: StateFlow<Authentication> get() = _authState
    private val requestScope = CoroutineScope(Dispatchers.IO)

    private fun doAsync(job: () -> Unit) {
        requestScope.launch { job() }
    }

    init {
        Log.d(TAG, "AuthRepository INIT")
    }

    fun insertPhoneNumber(phoneNumber: String) {
        Log.d("TelegramClient", "phoneNumber: $phoneNumber")

        val settings = TdApi.PhoneNumberAuthenticationSettings()
        doAsync {
            baseClient.send(TdApi.SetAuthenticationPhoneNumber(phoneNumber, settings)) {
                Log.d("TelegramClient", "phoneNumber. result: $it")
                when (it.constructor) {
                    TdApi.Ok.CONSTRUCTOR -> {

                    }

                    TdApi.Error.CONSTRUCTOR -> {

                    }
                }
            }
        }
    }

    fun insertCode(code: String) {
        Log.d("TelegramClient", "code: $code")
        doAsync {
            baseClient.send(TdApi.CheckAuthenticationCode(code)) {
                Log.d("TelegramClient", "code status: $it")
                when (it.constructor) {
                    TdApi.Ok.CONSTRUCTOR -> {
                        Log.d("TelegramClient", "Correct Code for Number")
                    }

                    TdApi.Error.CONSTRUCTOR -> {
                        setAuth(Authentication.INCORRECT_CODE)
                        Log.d("TelegramClient", "Incorrect Code for Number")
                    }
                }
            }
        }
    }

    fun insertPassword(password: String) {
        Log.d("TelegramClient", "inserting password")
        doAsync {
            baseClient.send(TdApi.CheckAuthenticationPassword(password)) {
                when (it.constructor) {
                    TdApi.Ok.CONSTRUCTOR -> {

                    }

                    TdApi.Error.CONSTRUCTOR -> {

                    }
                }
            }
        }
    }


    fun startAuthentication() {
        Log.d(TAG, "startAuthentication called")
        if (_authState.value != Authentication.UNAUTHENTICATED) {
            throw IllegalStateException("Start authentication called but client already authenticated. State: ${_authState.value}.")
        }

        doAsync {
            baseClient.send(TdApi.SetTdlibParameters(tdLibParameters)) {
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
}