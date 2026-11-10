
package com.mysketch.shared.domain.usecase

import com.mysketch.shared.domain.repository.StorageRepository

class GetStorageConfigUseCase(private val repository: StorageRepository) {
    operator fun invoke() = repository.getStorageConfig()
}
