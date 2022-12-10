package com.rzamau.storyapp.datasource.cache

import android.content.Context
import com.rzamau.storyapp.domain.model.Story
import kotlinx.datetime.LocalDateTime

class StoryDatabaseFactory(
    private val context: Context
) {
    fun createDatabase(): StoryDatabase {
        return StoryDatabase.createDatabase(context)
    }
}

fun StoryEntity.toStory(): Story {
    return Story(
        id = id,
        name = name,
        description = description,
        photoUrl = photoUrl,
        lat = lat,
        lon = lon,
        createdAt = LocalDateTime.parse(createdAt)
    )
}

fun Story.toStoryEntity(): StoryEntity {
    return StoryEntity(
        id = id,
        name = name,
        description = description,
        photoUrl = photoUrl,
        lat = lat,
        lon = lon,
        createdAt = createdAt.toString()
    )
}


fun List<StoryEntity>.toStoryList(): List<Story> {
    return map { it.toStory() }
}

fun List<Story>.toStoryEntityList(): List<StoryEntity> {
    return map { it.toStoryEntity() }
}


