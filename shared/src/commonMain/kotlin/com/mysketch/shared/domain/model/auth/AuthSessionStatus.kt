package com.mysketch.shared.domain.model.auth

enum class AuthSessionStatus {
    AUTHENTICATED,
    NOT_AUTHENTICATED,
    INITIALIZING,
    REFRESHING
}
