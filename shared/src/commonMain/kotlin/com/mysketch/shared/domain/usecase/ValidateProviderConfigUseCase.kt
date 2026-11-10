
package com.mysketch.shared.domain.usecase

import com.mysketch.shared.domain.model.StorageConfig
import com.mysketch.shared.domain.model.StorageProvider

class ValidateProviderConfigUseCase {
    operator fun invoke(config: StorageConfig, provider: StorageProvider): Boolean {
        return config.isProviderConfigured(provider)
    }
}
