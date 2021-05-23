package com.example.petproject.presentation.main_feed.all

import com.google.gson.annotations.SerializedName
import info.esoft.ko.data.model.rest.guber.AboutMe


data class WidgetFeed(
        var id: Int,
        var title: String? = null,
        var priority: Int = 0,
        @SerializedName("appeal") var guber: AboutMe? = null
//        @SerializedName("events") var events: List<Event>? = null
) {

    companion object {
        const val ABOUT_ME_WIDGET = 1
        const val EVENTS_WIDGET = 2
    }
}