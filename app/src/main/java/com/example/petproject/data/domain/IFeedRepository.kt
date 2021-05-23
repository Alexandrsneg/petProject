package com.example.petproject.data.domain

import com.example.petproject.common.rest.BaseRepository
import com.example.petproject.presentation.main_feed.all.WidgetFeed
import rx.Subscriber
import rx.Subscription


interface IFeedRepository {
    fun loadWidgetFeed(subscriber: Subscriber<MutableList<WidgetFeed>?>)
}

class FeedRepository(private val api: IFeedRestAPI) : BaseRepository(), IFeedRepository {

    override fun loadWidgetFeed(subscriber: Subscriber<MutableList<WidgetFeed>?>) {
        api.loadWidgetFeed().standardIO(subscriber)
    }
}