package com.mysketch.domain.model

data class AppReleaseInfo(val version: String, val releaseUrl: String)

sealed interface AppUpdateCheckResult {
    data object UpToDate : AppUpdateCheckResult
    data class UpdateAvailable(val release: AppReleaseInfo) : AppUpdateCheckResult
}
