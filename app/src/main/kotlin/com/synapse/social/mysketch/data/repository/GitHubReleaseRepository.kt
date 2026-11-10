package com.mysketch.data.repository

import com.mysketch.core.util.json
import com.mysketch.data.local.ReleaseCacheStore
import com.mysketch.data.model.GitHubReleaseDto
import com.mysketch.data.remote.GitHubReleaseApi
import com.mysketch.domain.model.release.AppRelease
import com.mysketch.domain.model.release.ReleaseFeedSource
import com.mysketch.domain.model.release.ReleaseSnapshot
import com.mysketch.domain.repository.AppReleaseRepository
import com.mysketch.shared.domain.exception.NetworkUnavailableException
import com.mysketch.shared.domain.repository.PlatformInfoProvider
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.SerializationException

/**
 * GitHub releases with a read-through cache: a request is only made when the stored copy is older
 * than [CACHE_TTL_MILLIS] or the caller forces it, and any failure that still has a stored copy is
 * served from it so the changelog works offline.
 */
@Singleton
class GitHubReleaseRepository @Inject constructor(
    private val api: GitHubReleaseApi,
    private val cache: ReleaseCacheStore,
    private val platformInfoProvider: PlatformInfoProvider
) : AppReleaseRepository {

    override fun observeCachedReleases(): Flow<List<AppRelease>> =
        cache.payload.map { payload -> decodeOrNull(payload) ?: emptyList() }

    override fun observeLastSeenTag(): Flow<String?> = cache.lastSeenTag

    override suspend fun loadReleases(forceRefresh: Boolean): Result<ReleaseSnapshot> {
        val storedPayload = cache.readPayload()
        val storedReleases = decodeOrNull(storedPayload)
        if (storedReleases != null && !forceRefresh && isFresh(cache.readFetchedAtMillis())) {
            return Result.success(ReleaseSnapshot(storedReleases, ReleaseFeedSource.Cache))
        }

        return try {
            if (!platformInfoProvider.isNetworkAvailable()) throw NetworkUnavailableException()
            val payload = api.fetchReleasePayload()
            val releases = decode(payload)
            cache.write(payload, System.currentTimeMillis())
            Result.success(ReleaseSnapshot(releases, ReleaseFeedSource.Network))
        } catch (cancelled: CancellationException) {
            throw cancelled
        } catch (failure: Exception) {
            if (storedReleases != null) {
                Result.success(ReleaseSnapshot(storedReleases, ReleaseFeedSource.CacheFallback))
            } else {
                Result.failure(failure)
            }
        }
    }

    override suspend fun markSeen(tag: String) {
        val trimmed = tag.trim()
        if (trimmed.isNotEmpty()) cache.markSeen(trimmed)
    }

    private fun isFresh(fetchedAtMillis: Long): Boolean =
        fetchedAtMillis > 0L && System.currentTimeMillis() - fetchedAtMillis < CACHE_TTL_MILLIS

    private fun decode(payload: String): List<AppRelease> =
        AppReleaseMapper.toDomainList(json.decodeFromString<List<GitHubReleaseDto>>(payload))

    /** `null` means there is no usable stored copy, which is different from a stored empty list. */
    private fun decodeOrNull(payload: String?): List<AppRelease>? = try {
        payload?.let(::decode)
    } catch (_: SerializationException) {
        null
    } catch (_: IllegalArgumentException) {
        null
    }

    private companion object {
        /** Long enough that opening Settings repeatedly costs no requests, short enough to stay useful. */
        const val CACHE_TTL_MILLIS = 6L * 60L * 60L * 1000L
    }
}
