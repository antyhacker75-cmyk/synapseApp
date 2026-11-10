package com.mysketch.shared.domain.usecase.chat

import com.mysketch.shared.domain.model.User
import com.mysketch.shared.domain.repository.ChatRepository
import javax.inject.Inject

class GetGroupMembersUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatId: String): Result<List<Pair<User, Boolean>>> {
        return chatRepository.getGroupMembers(chatId)
    }
}
