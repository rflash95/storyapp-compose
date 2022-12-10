package com.rzamau.storyapp.utils

import com.rzamau.storyapp.domain.model.Story
import com.rzamau.storyapp.domain.model.User
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object DataDummy {
    fun generateDummyStories(): List<Story> {
        val storyList = ArrayList<Story>()
        for (i in 0..10) {
            val story = Story(
                id = "story-$i",
                name = "name $i",
                description = "description $i",
                photoUrl = "https://images.unsplash.com/photo-1657214059175-53cb22261d38?ixlib=rb-4.0.3&dl=samsung-memory-k5eFm1f2esQ-unsplash.jpg&w=640&q=80&fm=jpg&crop=entropy&cs=tinysrgb",
                lat = -6.895173,
                lon = 107.607925,
                createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )
            storyList.add(story)
        }
        return storyList
    }

    fun loginResponse(): User {
        return User(
            id = "user-232134",
            name = "test@email.com",
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLVV6TlJuM2Fkam84MXRhS0EiLCJpYXQiOjE2Njg1Njc0Njd9.mwTzjr3jyxPdy28Eznx8ma6jhJ8DyxvrQple6QIgeUY"
        )
    }

}