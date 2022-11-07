package com.example.shop.ui

import java.security.MessageDigest

fun digest(password: String): String {
    val bytes = MessageDigest
        .getInstance("SHA-256")
        .digest(password.toByteArray())

    return bytes.toHexString()
}

fun ByteArray.toHexString() = joinToString("") { "%02x".format(it) }
