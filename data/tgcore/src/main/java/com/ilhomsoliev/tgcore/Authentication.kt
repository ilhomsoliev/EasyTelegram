package com.ilhomsoliev.tgcore

import org.drinkless.tdlib.TdApi

sealed class Authentication {
    object UNAUTHENTICATED : Authentication()

    object WAIT_FOR_NUMBER : Authentication()

    data class WAIT_FOR_CODE(
        val phoneNumber: String?,
        val type: TdApi.AuthenticationCodeType,
        val nextType: TdApi.AuthenticationCodeType? = null,
        val timeout: Int,
        val incorrectAttempt: Boolean = false,
    ) : Authentication()
    data class WAIT_FOR_PASSWORD(
        val passwordHint: String,
        val hasRecoveryEmailAddress: Boolean,
        val hasPassportData: Boolean,
        val recoveryEmailAddressPattern: String,
    ) : Authentication()

    object AUTHENTICATED : Authentication()
    object UNKNOWN : Authentication()
}