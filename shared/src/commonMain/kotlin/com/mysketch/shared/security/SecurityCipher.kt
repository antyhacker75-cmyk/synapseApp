package com.mysketch.shared.security

interface SecurityCipher {
    fun encrypt(plainText: String): String
    fun decrypt(cipherText: String): String
}
