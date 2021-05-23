package com.example.petproject.presentation.main_feed.all

import com.google.gson.annotations.SerializedName
import info.esoft.ko.data.model.rest.guber.AboutMe
import java.util.*

open class FeedItem(
    @SerializedName("feedType")
        var type: String = FEED_ITEM_TYPE_ENDED,
//    var poll: Poll? = null,
//    val news: News? = null,
//    var events: List<Event>? = null,
    var aboutMe: AboutMe? = null,
    var title: String? = null,
    var subTitle: String? = null
) {

    companion object {
        const val FEED_ITEM_TYPE_ENDED = "ended"
        const val FEED_ITEM_GUBER_WIDGET = "about_me"
        const val FEED_ITEM_EVENTS_WIDGET = "events"
    }

    fun getItemViewType(): Int {
        return when (type) {
            FEED_ITEM_GUBER_WIDGET -> return FeedAllAdapter.ABOUT_ME_WIDGET_TYPE
            FEED_ITEM_EVENTS_WIDGET -> return FeedAllAdapter.EVENTS_WIDGET_TYPE
            FEED_ITEM_TYPE_ENDED -> return FeedAllAdapter.ENDED_TYPE
            else -> 0
        }
    }

}

class FeedItemEnded : FeedItem()



data class FeedListItems(@SerializedName("isLast") val isLast: Boolean,
                         @SerializedName("data") var data: List<FeedItem>?) {
    lateinit var itemLastDate: Date
    lateinit var feedType: String
}



