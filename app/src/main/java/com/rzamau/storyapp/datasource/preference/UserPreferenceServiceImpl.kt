package com.rzamau.storyapp.datasource.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.rzamau.storyapp.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferenceServiceImpl(
    private val dataStore: DataStore<Preferences>
) : UserPreferenceService {
    private val USER_ID = stringPreferencesKey("user_id")
    private val USER_NAME = stringPreferencesKey("user_name")
    private val USER_TOKEN = stringPreferencesKey("user_token")


    override suspend fun saveUserToPreferencesStore(user: User) {
        dataStore.edit { preference ->
            preference[USER_ID] = user.id
            preference[USER_NAME] = user.name
            preference[USER_TOKEN] = user.token
        }
    }

    override suspend fun getUserFromPreferencesStore(): Flow<User> {
        return dataStore.data.map { preference ->
            User(
                id = preference[USER_ID] ?: "",
                name = preference[USER_NAME] ?: "",
                token = preference[USER_TOKEN] ?: ""
            )
        }
    }

    override suspend fun deleteUserFromPreferencesStore(): Boolean {
        dataStore.edit { preference ->
            preference.clear()
        }
        return true
    }
}