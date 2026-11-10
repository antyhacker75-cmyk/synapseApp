package com.mysketch.shared.domain.usecase

import com.mysketch.shared.domain.exception.NetworkUnavailableException
import com.mysketch.shared.domain.model.AppUpdateInfo
import com.mysketch.shared.domain.repository.AppRepository
import com.mysketch.shared.domain.repository.PlatformInfoProvider

class CheckForUpdatesUseCase(
    private val appRepository: AppRepository,
    private val platformInfoProvider: PlatformInfoProvider
) {
    suspend operator fun invoke(): Result<AppUpdateInfo?> {
        if (!platformInfoProvider.isNetworkAvailable()) {
            return Result.failure(NetworkUnavailableException())
        }
        val currentVersionCode = platformInfoProvider.getAppVersionCode()
        return appRepository.getAppUpdateInfo().map { updateInfo ->
            if (updateInfo.versionCode.toInt() > currentVersionCode) {
                updateInfo
            } else {
                null
            }
        }
    }
}
