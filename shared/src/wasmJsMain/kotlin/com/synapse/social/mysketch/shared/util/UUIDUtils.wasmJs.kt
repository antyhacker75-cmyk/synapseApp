package com.mysketch.shared.util

private fun cryptoRandomUUID(): String = js("self.crypto.randomUUID()")

actual object UUIDUtils {
    actual fun randomUUID(): String = cryptoRandomUUID()
}
