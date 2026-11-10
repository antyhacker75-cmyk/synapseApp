package com.mysketch.domain.usecase.post

import androidx.paging.PagingData
import com.mysketch.domain.repository.PostRepository
import com.mysketch.domain.model.FeedItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFeedPagedUseCase @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke(): Flow<PagingData<FeedItem>> = repository.getFeedPaged()
}
