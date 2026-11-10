package com.mysketch.domain.repository

import com.mysketch.domain.model.ChatVerificationInfo

interface ChatVerificationRepository {
    suspend fun load(chatId: String): Result<ChatVerificationInfo>
    suspend fun setVerified(info: ChatVerificationInfo, verified: Boolean): Result<Unit>
    suspend fun verifyScannedCode(info: ChatVerificationInfo, scannedCode: String): Result<Boolean>
}
