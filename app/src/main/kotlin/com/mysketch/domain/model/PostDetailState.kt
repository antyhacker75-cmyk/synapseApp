package com.mysketch.domain.model

sealed class PostDetailState {
    object Loading : PostDetailState()

    data class Success(val postDetail: PostDetail) : PostDetailState()

    data class Error(val message: String, val throwable: Throwable? = null) : PostDetailState()
}
