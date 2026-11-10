package com.mysketch.shared.domain.usecase

import com.mysketch.shared.core.util.LinkMetadataParser
import com.mysketch.shared.domain.model.LinkPreview
import com.mysketch.shared.domain.repository.LinkPreviewRepository

class GetLinkMetadataUseCase(
    private val repository: LinkPreviewRepository
) {
    suspend operator fun invoke(url: String): LinkPreview? {
        // Blank, malformed and non-http(s) input is rejected before any network work happens.
        val normalized = LinkMetadataParser.normalizeUrl(url) ?: return null
        return repository.getLinkPreview(normalized)
    }
}
