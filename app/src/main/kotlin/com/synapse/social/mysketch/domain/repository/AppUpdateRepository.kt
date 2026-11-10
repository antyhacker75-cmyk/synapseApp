package com.mysketch.domain.repository

import com.mysketch.domain.model.AppReleaseInfo

interface AppUpdateRepository {
    suspend fun getLatestRelease(): Result<AppReleaseInfo>
}
