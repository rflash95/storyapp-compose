package com.rzamau.storyapp.domain.model

import com.rzamau.storyapp.R
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class Story(
    val id: String,
    val name: String,
    val description: String,
    val photoUrl: String,
    val lat: Double?,
    val lon: Double?,
    val createdAt: LocalDateTime,
) {
    constructor() : this(
        id = "",
        name = "",
        description = "",
        photoUrl = "",
        lat = null,
        lon = null,
        createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    )
}


val dummyStory: List<Story> = listOf(
    Story(
        id = "story-UD2",
        name = "Reza",
        description = "Haiiiiii",
        photoUrl = "https://images.unsplash.com/photo-1657214059175-53cb22261d38?ixlib=rb-4.0.3&dl=samsung-memory-k5eFm1f2esQ-unsplash.jpg&w=640&q=80&fm=jpg&crop=entropy&cs=tinysrgb",
        lat = null,
        lon = null,
        createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    ),
    Story(
        id = "story-p22",
        name = "prabu",
        description = "Yeayyyy",
        photoUrl = "https://images.unsplash.com/photo-1587502537104-aac10f5fb6f7?ixlib=rb-4.0.3&dl=boxed-water-is-better-stpmvHj6C-o-unsplash.jpg&w=640&q=80&fm=jpg&crop=entropy&cs=tinysrgb",
        lat = null,
        lon = null,
        createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    ),
    Story(
        id = "story-P11",
        name = "Vana",
        description = "Nowhere",
        photoUrl = "https://unsplash.com/photos/h7llrunUaE0/download?ixid=MnwxMjA3fDF8MXxzZWFyY2h8MTV8fG5hdHVyZXxlbnwwfDF8fHwxNjY2NDc2MTE2&force=true&w=640",
        lat = null,
        lon = null,
        createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    )

)

val listWelcomeStory: List<Int> = listOf(
    R.drawable.photo_1,
    R.drawable.photo_2,
    R.drawable.photo_3
)