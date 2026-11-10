package com.mysketch.shared.domain.usecase.settings

import com.mysketch.shared.domain.model.settings.AppearanceSettings
import com.mysketch.shared.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow


class GetAppearanceSettingsUseCase constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Flow<AppearanceSettings> = repository.appearanceSettings
}
