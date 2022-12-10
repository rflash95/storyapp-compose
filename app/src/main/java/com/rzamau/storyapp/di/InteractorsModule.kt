package com.rzamau.storyapp.di

import DatetimeUtil
import android.content.Context
import com.rzamau.storyapp.datasource.cache.StoryDatabase
import com.rzamau.storyapp.datasource.network.AuthService
import com.rzamau.storyapp.datasource.network.StoryService
import com.rzamau.storyapp.datasource.preference.UserPreferenceService
import com.rzamau.storyapp.interactors.login.PostLogin
import com.rzamau.storyapp.interactors.pref.DeleteUserPref
import com.rzamau.storyapp.interactors.pref.GetUserPref
import com.rzamau.storyapp.interactors.register.PostRegister
import com.rzamau.storyapp.interactors.story_detail.GetStory
import com.rzamau.storyapp.interactors.story_list.GetStories
import com.rzamau.storyapp.interactors.story_list.GetStoriesPagingData
import com.rzamau.storyapp.interactors.story_upload.PostStory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractorsModule {

    @Singleton
    @Provides
    fun providePostLogin(
        authService: AuthService,
        userPreferenceService: UserPreferenceService
    ): PostLogin {
        return PostLogin(
            authService = authService,
            userPreferenceService = userPreferenceService
        )
    }

    @Singleton
    @Provides
    fun providePostRegister(
        authService: AuthService,
    ): PostRegister {
        return PostRegister(
            authService = authService,
        )
    }

    @Singleton
    @Provides
    fun provideGetStories(
        storyService: StoryService,
        storyDatabase: StoryDatabase,
        userPreferenceService: UserPreferenceService
    ): GetStories {
        return GetStories(
            storyService = storyService,
            storyDatabase = storyDatabase,
            userPreferenceService = userPreferenceService,
        )
    }

    @Singleton
    @Provides
    fun provideGetStory(
        storyDatabase: StoryDatabase
    ): GetStory {
        return GetStory(
            storyDatabase = storyDatabase,
        )
    }

    @Singleton
    @Provides
    fun providePostStory(
        storyService: StoryService,
        @ApplicationContext context: Context,
        userPreferenceService: UserPreferenceService
    ): PostStory {
        return PostStory(
            storyService = storyService,
            userPreferenceService = userPreferenceService,
            context = context,
        )
    }

    @Singleton
    @Provides
    fun provideGetUserPref(
        userPreferenceService: UserPreferenceService
    ): GetUserPref {
        return GetUserPref(
            userPreferenceService = userPreferenceService,
        )
    }

    @Singleton
    @Provides
    fun provideDeleteUserPref(
        userPreferenceService: UserPreferenceService
    ): DeleteUserPref {
        return DeleteUserPref(
            userPreferenceService = userPreferenceService,
        )
    }

    @Singleton
    @Provides
    fun provideGetStoriesPagingData(
        storyService: StoryService,
        userPreferenceService: UserPreferenceService,
        storyDatabase: StoryDatabase
    ): GetStoriesPagingData {
        return GetStoriesPagingData(
            storyService = storyService,
            userPreferenceService = userPreferenceService,
            storyDatabase = storyDatabase,
            datetimeUtil = DatetimeUtil(),
        )
    }

}