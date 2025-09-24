package com.towhid.postapp.utils

enum class LoginResult(val msg: String) {
    SUCCESS("Login Success"),
    WRONG_EMAIL("Wrong Email"),
    WRONG_PASSWORD("Wrong Password"),
    FAILED("Login Failed")
}