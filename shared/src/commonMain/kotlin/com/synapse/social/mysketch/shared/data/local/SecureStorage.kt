package com.mysketch.shared.data.local

interface SecureStorage {
    fun save(key: String, value: String)
    fun getString(key: String): String?
    fun clear(key: String)
}
