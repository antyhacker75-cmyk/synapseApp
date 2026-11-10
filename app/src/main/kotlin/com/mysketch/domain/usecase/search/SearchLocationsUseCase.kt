package com.mysketch.domain.usecase.search

import com.mysketch.data.repository.LocationRepositoryImpl
import com.mysketch.domain.model.LocationData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchLocationsUseCase @Inject constructor(
    private val locationRepository: LocationRepositoryImpl
) {
    operator fun invoke(query: String): Flow<Result<List<LocationData>>> = flow {
        if (query.isBlank()) {
            emit(Result.success(emptyList()))
            return@flow
        }
        delay(500)

        val result = locationRepository.searchLocations(query)
        emit(result)
    }
}
