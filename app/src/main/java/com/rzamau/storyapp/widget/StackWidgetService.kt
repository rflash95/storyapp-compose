package com.rzamau.storyapp.widget

import android.content.Intent
import android.widget.RemoteViewsService
import com.rzamau.storyapp.datasource.cache.StoryDatabase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StackWidgetService : RemoteViewsService() {
    @Inject
    lateinit var storyCache: StoryDatabase
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory =
        StackRemoteViewsFactory(this.applicationContext, storyCache)
}