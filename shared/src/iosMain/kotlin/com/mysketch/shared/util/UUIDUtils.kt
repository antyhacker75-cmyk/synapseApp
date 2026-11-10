package com.mysketch.shared.util

import platform.Foundation.NSUUID

actual object UUIDUtils {
    actual fun randomUUID(): String {
        return NSUUID().UUIDString
    }
}
