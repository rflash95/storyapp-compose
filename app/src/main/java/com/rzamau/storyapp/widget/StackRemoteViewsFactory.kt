package com.rzamau.storyapp.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.rzamau.storyapp.R
import com.rzamau.storyapp.datasource.cache.StoryDatabase
import java.net.URL

internal class StackRemoteViewsFactory(
    private val mContext: Context,
    private val storyDatabase: StoryDatabase
) :
    RemoteViewsService.RemoteViewsFactory {

    private val mWidgetItems = ArrayList<Bitmap>()

    override fun onCreate() {
    }

    override fun onDataSetChanged() {
        val identityToken = Binder.clearCallingIdentity()


        val stories = storyDatabase.storyDao().getAllStory(5)
        stories.forEach { story ->
            mWidgetItems.add(getImageFromUrl(story.photoUrl))
        }


        Binder.restoreCallingIdentity(identityToken)


    }

    override fun onDestroy() {
    }

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        rv.setImageViewBitmap(R.id.imageView, mWidgetItems[position])
        val extras = bundleOf(
            ImagesBannerWidget.EXTRA_ITEM to position
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null


    override fun getViewTypeCount(): Int = 1


    override fun getItemId(i: Int): Long = 0


    override fun hasStableIds(): Boolean = false

    private fun getImageFromUrl(imageUrl: String): Bitmap =
        URL(imageUrl).openStream().use {
            BitmapFactory.decodeStream(it)
        }


}
