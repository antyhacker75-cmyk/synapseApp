package com.mysketch.shared.domain.usecase.chat

import com.mysketch.shared.domain.model.chat.ChatInfo
import com.mysketch.shared.domain.repository.ChatRepository
import javax.inject.Inject

class GetChatInfoUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatId: String): Result<ChatInfo?> {
        return chatRepository.getChatInfo(chatId)
    }
}
