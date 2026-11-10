package com.mysketch.domain.repository

import com.mysketch.domain.model.OpenSourceLicense

interface OpenSourceLicenseRepository {
    suspend fun getLicenses(): Result<List<OpenSourceLicense>>
}
