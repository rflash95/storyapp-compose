package com.rzamau.storyapp.interactors.pref

import com.rzamau.storyapp.datasource.preference.UserPreferenceService
import com.rzamau.storyapp.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class DeleteUserPref(
    private val userPreferenceService: UserPreferenceService
) {
    fun execute(): Flow<DataState<Boolean>> = flow {
        try {
            emit(DataState.loading())
            val delete = userPreferenceService.deleteUserFromPreferencesStore()
            emit(DataState.data(message = null, data = delete))
        } catch (e: Exception) {
            emit(DataState.error(message = e.message))
        }
    }
}