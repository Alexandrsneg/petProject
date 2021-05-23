package com.example.petproject.data.model.rest.guber

import android.os.Parcelable
import com.example.petproject.data.model.rest.guber.Site
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contacts(
        @SerializedName("phone_description") val phoneDescription: String? = null,
        val phone: String? = null,
        val mail: String? = null,
        val sites: List<Site>? = null
) : Parcelable