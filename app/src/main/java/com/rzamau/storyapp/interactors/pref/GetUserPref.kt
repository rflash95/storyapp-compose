package com.rzamau.storyapp.interactors.pref

import com.rzamau.storyapp.datasource.preference.UserPreferenceService
import com.rzamau.storyapp.domain.model.User
import com.rzamau.storyapp.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow


class GetUserPref(
    private val userPreferenceService: UserPreferenceService
) {
    fun execute(): Flow<DataState<User>> = flow {
        try {
            emit(DataState.loading())
            val userPref = userPreferenceService.getUserFromPreferencesStore().first()
            emit(DataState.data(message = null, data = userPref))
        } catch (e: Exception) {
            emit(DataState.error(message = e.message))
        }
    }
}