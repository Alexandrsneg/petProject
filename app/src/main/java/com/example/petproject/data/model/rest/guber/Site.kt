package com.example.petproject.data.model.rest.guber

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Site(
        val url: String,
        val title: String? = null
) : Parcelable