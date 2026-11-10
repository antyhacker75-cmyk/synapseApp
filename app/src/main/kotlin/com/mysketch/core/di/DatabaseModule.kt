package com.mysketch.core.di

import com.mysketch.data.local.database.SettingsDataStore
import android.content.Context
import com.mysketch.core.config.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.mysketch.shared.data.database.StorageDatabase
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.mysketch.shared.data.database.Post
import com.mysketch.shared.data.database.Comment
import com.mysketch.shared.data.database.User
import com.mysketch.shared.data.database.*
import com.mysketch.shared.data.local.database.PostDao
import com.mysketch.shared.data.local.database.SqlDelightPostDao
import com.mysketch.shared.data.local.database.PendingActionDao
import com.mysketch.shared.data.local.database.SqlDelightPendingActionDao
import com.mysketch.shared.data.local.database.CachedMessageDao
import com.mysketch.shared.data.local.database.SqlDelightCachedMessageDao
import com.mysketch.shared.data.local.database.CachedConversationDao
import com.mysketch.shared.data.local.database.SqlDelightCachedConversationDao
import com.mysketch.shared.data.local.database.StoryDao
import com.mysketch.shared.data.local.database.SqlDelightStoryDao
import com.mysketch.shared.data.local.database.UserDao
import com.mysketch.shared.data.local.database.UserDaoImpl
import com.mysketch.shared.security.SecurityCipher
import com.mysketch.shared.security.AndroidSecurityCipher
import com.mysketch.shared.data.adapter.EncryptedStringAdapter

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideSettingsDataStore(@ApplicationContext context: Context): SettingsDataStore {
        return SettingsDataStore.getInstance(context)
    }


    @Provides
    fun providePendingActionDao(db: StorageDatabase): PendingActionDao {
        return SqlDelightPendingActionDao(db)
    }

    @Provides
    fun providePostDao(db: StorageDatabase): PostDao {
        return SqlDelightPostDao(db)
    }

    @Provides
    fun provideUserDao(db: StorageDatabase): UserDao {
        return UserDaoImpl(db)
    }

    @Provides
    fun provideCachedMessageDao(db: StorageDatabase): CachedMessageDao {
        return SqlDelightCachedMessageDao(db)
    }

    @Provides
    fun provideCachedConversationDao(db: StorageDatabase): CachedConversationDao {
        return SqlDelightCachedConversationDao(db)
    }

    @Provides
    fun provideStoryDao(db: StorageDatabase): StoryDao {
        return SqlDelightStoryDao(db)
    }

    @Provides
    @Singleton
    fun provideSecurityCipher(): SecurityCipher {
        return AndroidSecurityCipher()
    }

    @Provides
    @Singleton
    fun provideStorageDatabase(@ApplicationContext context: Context, securityCipher: SecurityCipher): StorageDatabase {
        val driver = AndroidSqliteDriver(StorageDatabase.Schema, context, AppConstants.DATABASE_NAME)
        return StorageDatabase(
            driver = driver,
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
                syncVersionAdapter = intAdapter
            ),
            CommentAdapter = Comment.Adapter(
                likesCountAdapter = intAdapter,
                repliesCountAdapter = intAdapter,
                linkPreviewAdapter = com.mysketch.shared.data.database.linkPreviewAdapter,
                viewsCountAdapter = intAdapter,
                syncVersionAdapter = intAdapter
            ),
            UserAdapter = User.Adapter(
                emailAdapter = EncryptedStringAdapter(securityCipher),
                followersCountAdapter = intAdapter,
                followingCountAdapter = intAdapter,
                postsCountAdapter = intAdapter,
                syncVersionAdapter = intAdapter
            ),
            PendingActionAdapter = com.mysketch.shared.data.database.PendingAction.Adapter(
                retryCountAdapter = intAdapter,
                localVersionAdapter = intAdapter
            )
        )
    }
}
