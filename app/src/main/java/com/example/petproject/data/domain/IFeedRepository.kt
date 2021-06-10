package com.example.petproject.data.domain

import android.content.ContentValues.TAG
import android.util.Log
import com.example.petproject.common.rest.BaseRepository
import com.example.petproject.presentation.main_feed.all.WidgetFeed
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import rx.Subscriber


interface IFeedRepository {
    fun loadWidgetFeed(subscriber: Subscriber<MutableList<WidgetFeed>?>)
}

class FeedRepository(private val api: IFeedRestAPI) : BaseRepository(), IFeedRepository {

    override fun loadWidgetFeed(subscriber: Subscriber<MutableList<WidgetFeed>?>) {
        api.loadWidgetFeed().standardIO(subscriber)
    }
}