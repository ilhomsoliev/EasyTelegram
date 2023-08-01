package com.ilhomsoliev.data.auth

enum class Authentication {
    UNAUTHENTICATED,
    WAIT_FOR_NUMBER,
    WAIT_FOR_CODE,
    INCORRECT_CODE,
    WAIT_FOR_PASSWORD,
    AUTHENTICATED,
    UNKNOWN
}