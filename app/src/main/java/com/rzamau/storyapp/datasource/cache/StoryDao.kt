package com.rzamau.storyapp.datasource.cache

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(stories: List<StoryEntity>)

    @Query("SELECT * FROM story_entity")
    fun getStoryPagingSource(): PagingSource<Int, StoryEntity>

    @Query("SELECT * FROM story_entity WHERE id = :id")
    suspend fun getStory(id: String): StoryEntity

    @Query("SELECT * FROM story_entity LIMIT :limit")
    fun getAllStory(limit: Int): List<StoryEntity>

    @Query("SELECT * FROM story_entity WHERE lat NOT NULL")
    suspend fun getAllOnlyHasLocation(): List<StoryEntity>

    @Query("DELETE FROM story_entity")
    suspend fun deleteAll()
}