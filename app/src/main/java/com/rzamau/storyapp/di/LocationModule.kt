package com.rzamau.storyapp.di

import com.rzamau.storyapp.BaseApplication
import com.rzamau.storyapp.presentation.story_upload.LocationListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {
    @Singleton
    @Provides
    fun provideLocationListener(context: BaseApplication): LocationListener {
        return LocationListener(context)
    }

}