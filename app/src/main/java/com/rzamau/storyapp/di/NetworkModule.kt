package com.rzamau.storyapp.di

import com.rzamau.storyapp.datasource.network.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return KtorClientFactory().build()
    }

    @Singleton
    @Provides
    fun provideAuthService(
        httpClient: HttpClient
    ): AuthService {
        return AuthServiceImpl(
            httpClient = httpClient
        )
    }

    @Singleton
    @Provides
    fun provideStoryService(
        httpClient: HttpClient
    ): StoryService {
        return StoryServiceImpl(
            httpClient = httpClient
        )
    }
}