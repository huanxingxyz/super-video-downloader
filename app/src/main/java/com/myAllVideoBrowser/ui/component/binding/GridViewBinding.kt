package com.myAllVideoBrowser.ui.component.binding

import android.widget.GridView
import androidx.databinding.BindingAdapter
import com.myAllVideoBrowser.data.local.room.entity.PageInfo
import com.myAllVideoBrowser.ui.component.adapter.*

object GridViewBinding {
    @BindingAdapter("app:topPages")
    @JvmStatic
    fun GridView.setTopPages(items: List<PageInfo>) {
        with(adapter as TopPageAdapter?) {
            this?.let { setData(items) }
        }
    }

    @BindingAdapter("app:bookmarks")
    @JvmStatic
    fun GridView.setBookmarks(items: MutableList<PageInfo>) {
        with(adapter as BookmarksAdapter?) {
            this?.let { setData(items) }
        }
    }
}