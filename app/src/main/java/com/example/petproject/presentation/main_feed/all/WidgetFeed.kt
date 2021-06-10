package com.example.petproject.presentation.main_feed.all

import com.google.gson.annotations.SerializedName
import info.esoft.ko.data.model.rest.guber.AboutMe


data class WidgetFeed constructor(
        var id: Int,
        var title: String? = null,
        var aboutMe: AboutMe? = null
//        @SerializedName("events") var events: List<Event>? = null
) {
    constructor() : this(0)
    companion object {
        const val ABOUT_ME_WIDGET = 1
        const val EVENTS_WIDGET = 2
    }
}