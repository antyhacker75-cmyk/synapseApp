package com.mysketch.core.di

import com.mysketch.shared.data.database.StorageDatabase
import com.mysketch.shared.data.local.database.UserDao
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DatabaseEntryPoint {
    fun getUserDao(): UserDao
}
