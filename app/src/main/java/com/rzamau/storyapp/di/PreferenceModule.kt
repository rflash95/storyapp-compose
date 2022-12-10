package com.rzamau.storyapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.rzamau.storyapp.datasource.preference.UserDataStoreFactory
import com.rzamau.storyapp.datasource.preference.UserPreferenceService
import com.rzamau.storyapp.datasource.preference.UserPreferenceServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return UserDataStoreFactory().build(context)
    }


    @Singleton
    @Provides
    fun provideUserPreferenceService(
        datastore: DataStore<Preferences>
    ): UserPreferenceService {
        return UserPreferenceServiceImpl(dataStore = datastore)
    }

}