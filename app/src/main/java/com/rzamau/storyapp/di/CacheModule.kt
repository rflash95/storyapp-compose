package com.rzamau.storyapp.di

import com.rzamau.storyapp.BaseApplication
import com.rzamau.storyapp.datasource.cache.StoryDatabase
import com.rzamau.storyapp.datasource.cache.StoryDatabaseFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {
    @Singleton
    @Provides
    fun provideStoryDatabase(context: BaseApplication): StoryDatabase {
        return StoryDatabaseFactory(context).createDatabase()
    }

}