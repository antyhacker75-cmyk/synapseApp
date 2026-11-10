package com.mysketch.shared.domain.usecase.device

import com.mysketch.shared.data.datasource.DeviceRegistrationDataSource

class UpdateDevicePresenceUseCase(
    private val deviceRegistrationDataSource: DeviceRegistrationDataSource
) {
    suspend operator fun invoke(deviceId: String): Result<Unit> {
        return deviceRegistrationDataSource.updateDevicePresence(deviceId)
    }
}
