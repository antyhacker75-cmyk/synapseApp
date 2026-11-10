package com.mysketch.shared.util

fun ByteArray.toHexString(): String {
    return joinToString("") {
        val hex = it.toUByte().toString(16)
        if (hex.length == 1) "0$hex" else hex
    }
}