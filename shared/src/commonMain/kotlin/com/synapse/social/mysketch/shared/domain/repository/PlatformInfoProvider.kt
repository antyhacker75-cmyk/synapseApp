package com.mysketch.shared.domain.repository

interface PlatformInfoProvider {
    fun getAppVersionCode(): Int
    fun isNetworkAvailable(): Boolean
}
