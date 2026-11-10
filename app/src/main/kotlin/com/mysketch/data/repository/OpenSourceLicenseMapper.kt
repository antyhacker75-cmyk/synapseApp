package com.mysketch.data.repository

import com.mysketch.data.model.OpenSourceLicenseDto
import com.mysketch.domain.model.OpenSourceLicense

object OpenSourceLicenseMapper {

    fun toDomain(dto: OpenSourceLicenseDto): OpenSourceLicense = OpenSourceLicense(
        name = dto.name.trim(),
        author = dto.author.trim(),
        licenseId = dto.licenseId.trim().lowercase(),
        licenseNote = dto.licenseNote?.trim()?.ifBlank { null },
        repositoryUrl = dto.repositoryUrl.trim()
    )
}
