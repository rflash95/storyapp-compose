package com.rzamau.storyapp.interactors.story_list

import DatetimeUtil
import androidx.paging.*
import com.rzamau.storyapp.datasource.cache.StoryDatabase
import com.rzamau.storyapp.datasource.cache.toStory
import com.rzamau.storyapp.datasource.network.StoryService
import com.rzamau.storyapp.datasource.paging.StoryPagingMediator
import com.rzamau.storyapp.datasource.preference.UserPreferenceService
import com.rzamau.storyapp.domain.model.Story
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
class GetStoriesPagingData(
    private val storyService: StoryService,
    private val storyDatabase: StoryDatabase,
    private val userPreferenceService: UserPreferenceService,
    private val datetimeUtil: DatetimeUtil
) {
    fun execute(): Flow<PagingData<Story>> = Pager(
        config = PagingConfig(
            pageSize = 10
        ),
        remoteMediator = StoryPagingMediator(
            storyDatabase = storyDatabase, storyService = storyService,
            userPreferenceService = userPreferenceService,
            datetimeUtil = datetimeUtil
        ),
        pagingSourceFactory = {
            storyDatabase.storyDao().getStoryPagingSource()
        }
    ).flow.map { pagingData ->
        pagingData.map {
            it.toStory()
        }
    }
}


