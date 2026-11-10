package com.mysketch.shared.domain.usecase.device

import com.mysketch.shared.data.datasource.DeviceRegistrationDataSource

class RegisterDeviceUseCase(
    private val deviceRegistrationDataSource: DeviceRegistrationDataSource
) {
    suspend operator fun invoke(
        deviceToken: String,
        deviceName: String? = null,
        deviceType: String? = null
    ): Result<String> {
        return deviceRegistrationDataSource.registerDevice(deviceToken, deviceName, deviceType)
    }
}
