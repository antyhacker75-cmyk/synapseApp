package com.mysketch.shared.domain.repository

import com.mysketch.shared.domain.model.AppUpdateInfo

interface AppRepository {
    suspend fun getAppUpdateInfo(): Result<AppUpdateInfo>
}
