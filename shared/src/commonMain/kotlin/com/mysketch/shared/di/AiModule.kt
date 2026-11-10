package com.mysketch.shared.di

import com.mysketch.shared.data.repository.ai.GeminiAiRepository
import com.mysketch.shared.domain.repository.ai.AiRepository
import com.mysketch.shared.domain.usecase.ai.GenerateSmartRepliesUseCase
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val aiModule = module {
    single<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    coerceInputValues = true
                })
            }
        }
    }
    single<AiRepository> { GeminiAiRepository(get()) }
    single { GenerateSmartRepliesUseCase(get()) }
}
