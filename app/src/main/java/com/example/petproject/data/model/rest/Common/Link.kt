package com.example.petproject.data.model.rest.Common

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Link(
        val id: Int? = null,
        @SerializedName(value = "url", alternate = ["href"])
        val url: String,
        val title: String? = null
) : Parcelable