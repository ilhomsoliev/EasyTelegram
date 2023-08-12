package com.ilhomsoliev.auth

import android.util.Log
import com.ilhomsoliev.tgcore.TelegramClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.drinkless.td.libcore.telegram.Client
import org.drinkless.td.libcore.telegram.TdApi

class AuthRepository(
    private val client: TelegramClient
) : Client.ResultHandler {
    private val TAG = AuthRepository::class.java.simpleName

    private val _authState = MutableStateFlow(Authentication.UNKNOWN)
    val authState: StateFlow<Authentication> get() = _authState

    init {
        client.baseClient.send(TdApi.GetAuthorizationState(), this)
    }

    private fun setAuth(auth: Authentication) {
        _authState.value = auth
    }

    private val requestScope = CoroutineScope(Dispatchers.IO)

    private fun doAsync(job: () -> Unit) {
        requestScope.launch { job() }
    }

    override fun onResult(data: TdApi.Object) {
        Log.d(TAG, "onResult: ${data::class.java.simpleName}")
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
                client.baseClient.send(TdApi.CheckDatabaseEncryptionKey()) {
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

    fun insertPhoneNumber(phoneNumber: String) {
        Log.d("TelegramClient", "phoneNumber: $phoneNumber")

        val settings = TdApi.PhoneNumberAuthenticationSettings()
        doAsync {
            client.baseClient.send(TdApi.SetAuthenticationPhoneNumber(phoneNumber, settings)) {
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
            client.baseClient.send(TdApi.CheckAuthenticationCode(code)) {
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
            client.baseClient.send(TdApi.CheckAuthenticationPassword(password)) {
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
            client.baseClient.send(TdApi.SetTdlibParameters(client.tdLibParameters)) {
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