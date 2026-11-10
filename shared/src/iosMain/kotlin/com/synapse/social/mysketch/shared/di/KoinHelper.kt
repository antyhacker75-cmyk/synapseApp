package com.mysketch.shared.di

import com.mysketch.shared.data.repository.UserRepositoryImpl
import com.mysketch.shared.data.repository.UserPreferencesRepositoryImpl
import com.mysketch.shared.core.network.SupabaseClient
import com.mysketch.shared.domain.usecase.user.GetUserProfileUseCase
import com.mysketch.shared.domain.usecase.user.UpdateProfileUseCase
import com.mysketch.shared.domain.usecase.blocking.BlockUserUseCase
import com.mysketch.shared.domain.usecase.blocking.UnblockUserUseCase
import com.mysketch.shared.domain.usecase.blocking.GetBlockedUsersUseCase
import com.mysketch.shared.domain.model.User
import com.mysketch.shared.domain.model.BlockedUser
import com.mysketch.shared.domain.model.Comment
import com.mysketch.shared.domain.repository.AuthRepository
import com.mysketch.shared.domain.usecase.ai.GenerateSmartRepliesUseCase
import com.mysketch.shared.domain.repository.CommentRepository
import com.mysketch.shared.domain.usecase.comment.AddCommentUseCase
import com.mysketch.shared.domain.usecase.comment.GetCommentsUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object IOSDependencies : KoinComponent {
    // Inject lazily so it doesn't crash if accessed before startKoin
    private val database: com.mysketch.shared.data.database.StorageDatabase by inject()

    fun getUserRepository() = UserRepositoryImpl(database, com.mysketch.shared.data.datasource.SupabaseUserDataSource(SupabaseClient.client))
    fun getUserPreferencesRepository() = UserPreferencesRepositoryImpl(SupabaseClient.client)

    // Inject repositories from Koin
    fun getAuthRepository(): AuthRepository = getKoin().get()
    fun getStorageRepository(): com.mysketch.shared.domain.repository.StorageRepository = getKoin().get()
    fun getGenerateSmartRepliesUseCase(): GenerateSmartRepliesUseCase = getKoin().get()
    fun getCommentRepository(): CommentRepository = getKoin().get()

    // Create use cases on demand
    private fun getProfileUseCase() = GetUserProfileUseCase(getUserRepository())
    private fun getUpdateProfileUseCase() = UpdateProfileUseCase(getUserRepository())
    private fun getCommentsUseCase() = GetCommentsUseCase(getCommentRepository())
    private fun getAddCommentUseCase() = AddCommentUseCase(getCommentRepository())

    // iOS-specific wrappers that unwrap kotlin.Result for Swift async throws bridging
    @Throws(Exception::class)
    suspend fun getComments(postId: String, parentId: String? = null): List<Comment> {
        return getCommentsUseCase()(postId, parentId).getOrThrow()
    }

    @Throws(Exception::class)
    suspend fun addComment(postId: String, content: String, parentId: String?, mediaUrl: String? = null): Comment {
        return getAddCommentUseCase()(postId, content, parentId, mediaUrl).getOrThrow()
    }

    @Throws(Exception::class)
    suspend fun fetchUserProfile(uid: String): User? {
        val result = getProfileUseCase()(uid)
        return result.getOrElse { throw it }
    }

    @Throws(Exception::class)
    suspend fun saveUserProfile(uid: String, updates: Map<String, Any?>): Boolean {
        val result = getUpdateProfileUseCase()(uid, updates)
        return result.getOrElse { throw it }
    }
}
