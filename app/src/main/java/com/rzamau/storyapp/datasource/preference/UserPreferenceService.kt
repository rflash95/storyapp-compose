package com.rzamau.storyapp.datasource.preference

import com.rzamau.storyapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserPreferenceService {
    suspend fun saveUserToPreferencesStore(user: User)
    suspend fun getUserFromPreferencesStore(): Flow<User>
    suspend fun deleteUserFromPreferencesStore(): Boolean
}