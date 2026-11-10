package com.mysketch.shared.di

import com.mysketch.shared.data.repository.SupabaseCommentRepository
import com.mysketch.shared.domain.repository.CommentRepository
import com.mysketch.shared.domain.usecase.comment.AddCommentUseCase
import com.mysketch.shared.domain.usecase.comment.GetCommentsUseCase
import org.koin.dsl.module

val commentModule = module {
    single<CommentRepository> { SupabaseCommentRepository(get()) }
    factory { GetCommentsUseCase(get()) }
    factory { AddCommentUseCase(get()) }
}
