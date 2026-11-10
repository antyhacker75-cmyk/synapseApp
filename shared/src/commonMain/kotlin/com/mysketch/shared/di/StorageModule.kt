package com.mysketch.shared.di

import com.mysketch.shared.domain.repository.FileUploader
import com.mysketch.shared.data.database.StorageDatabase
import com.mysketch.shared.data.database.Post
import com.mysketch.shared.data.database.Comment
import com.mysketch.shared.data.database.User
import com.mysketch.shared.data.database.StoryEntity
import com.mysketch.shared.data.database.linkPreviewListAdapter
import com.mysketch.shared.data.database.mediaItemListAdapter
import com.mysketch.shared.data.database.pollOptionListAdapter
import com.mysketch.shared.data.database.reactionMapAdapter
import com.mysketch.shared.data.database.reactionTypeAdapter
import com.mysketch.shared.data.database.stringMapAdapter
import com.mysketch.shared.data.database.postMetadataAdapter
import com.mysketch.shared.data.database.intAdapter
import com.mysketch.shared.data.database.booleanAdapter
import com.mysketch.shared.data.repository.StorageRepositoryImpl
import com.mysketch.shared.data.local.database.PendingActionDao
import com.mysketch.shared.data.local.database.SqlDelightPendingActionDao
import com.mysketch.shared.data.local.database.CachedMessageDao
import com.mysketch.shared.data.local.database.SqlDelightCachedMessageDao
import com.mysketch.shared.data.local.database.CachedConversationDao
import com.mysketch.shared.data.local.database.SqlDelightCachedConversationDao
import com.mysketch.shared.data.local.database.MessageReactionDao
import com.mysketch.shared.data.local.database.SqlDelightMessageReactionDao
import com.mysketch.shared.data.local.database.StoryDao
import com.mysketch.shared.data.local.database.SqlDelightStoryDao
import com.mysketch.shared.data.adapter.EncryptedStringAdapter


import com.mysketch.shared.data.repository.MediaUploadRepositoryImpl
import com.mysketch.shared.data.repository.OfflineActionRepositoryImpl
import com.mysketch.shared.data.source.remote.CloudinaryUploadService
import com.mysketch.shared.data.source.remote.ImgBBUploadService
import com.mysketch.shared.data.source.remote.R2UploadService
import com.mysketch.shared.data.source.remote.SupabaseUploadService
import com.mysketch.shared.domain.repository.MediaUploadRepository
import com.mysketch.shared.domain.repository.StorageRepository
import com.mysketch.shared.domain.repository.OfflineActionRepository
import com.mysketch.shared.domain.usecase.GetStorageConfigUseCase
import com.mysketch.shared.domain.usecase.UpdateStorageProviderUseCase
import com.mysketch.shared.domain.usecase.UploadMediaUseCase
import com.mysketch.shared.domain.usecase.ValidateProviderConfigUseCase


import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module
import app.cash.sqldelight.db.SqlDriver

expect val storageDriverModule: Module
expect val fileUploaderModule: Module

val storageModule = module {
    includes(storageDriverModule)
    includes(fileUploaderModule)
    includes(secureStorageModule)

    single {
        StorageDatabase(
            driver = get(),
            PostAdapter = Post.Adapter(
                mediaItemsAdapter = mediaItemListAdapter,
                linkPreviewsAdapter = linkPreviewListAdapter,
                pollOptionsAdapter = pollOptionListAdapter,
                reactionsAdapter = reactionMapAdapter,
                userReactionAdapter = reactionTypeAdapter,
                metadataAdapter = postMetadataAdapter,
                likesCountAdapter = intAdapter,
                commentsCountAdapter = intAdapter,
                viewsCountAdapter = intAdapter,
                resharesCountAdapter = intAdapter,
                userPollVoteAdapter = intAdapter,
                encryptedContentAdapter = stringMapAdapter,
                // New sync columns
                syncVersionAdapter = intAdapter
            ),
            CommentAdapter = Comment.Adapter(
                likesCountAdapter = intAdapter,
                repliesCountAdapter = intAdapter,
                linkPreviewAdapter = com.mysketch.shared.data.database.linkPreviewAdapter,
                viewsCountAdapter = intAdapter,
                // New sync column
                syncVersionAdapter = intAdapter
            ),
            UserAdapter = User.Adapter(
                emailAdapter = EncryptedStringAdapter(get()),
                followersCountAdapter = intAdapter,
                followingCountAdapter = intAdapter,
                postsCountAdapter = intAdapter,
                // New sync column
                syncVersionAdapter = intAdapter
            ),
            PendingActionAdapter = com.mysketch.shared.data.database.PendingAction.Adapter(
                retryCountAdapter = intAdapter,
                localVersionAdapter = intAdapter
            )
        )
    }

    single<StorageRepository> { StorageRepositoryImpl(get(), get()) }
    single<PendingActionDao> { SqlDelightPendingActionDao(get()) }
    single<CachedMessageDao> { SqlDelightCachedMessageDao(get()) }
    single<CachedConversationDao> { SqlDelightCachedConversationDao(get()) }
    single<MessageReactionDao> { SqlDelightMessageReactionDao(get()) }
    single<StoryDao> { SqlDelightStoryDao(get()) }
    single<OfflineActionRepository> { OfflineActionRepositoryImpl(get()) }


    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
    }

    single { CloudinaryUploadService(get()) }
    single { ImgBBUploadService(get()) }
    single { SupabaseUploadService(get()) }
    single { R2UploadService(get()) }

    single { GetStorageConfigUseCase(get()) }
    single { UpdateStorageProviderUseCase(get()) }
    single { ValidateProviderConfigUseCase() }

    single<MediaUploadRepository> { MediaUploadRepositoryImpl(get(), get(), get(), get(), get()) }
    single { UploadMediaUseCase(get(), get(), get(), get()) }

}
