package com.example.petproject.data.model.rest.guber

import android.os.Parcelable
import com.example.petproject.data.model.rest.Attachment
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Localisation(
        val title: String? = null,
        val desc: String? = null,
        val subtitle: String? = null,
        val content: String? = null,
        @SerializedName("sub_desc") val subDesc: String? = null,
        @SerializedName("button_text") val buttonText: String? = null,
        @SerializedName("attachments") val attachments: List<Attachment>? = null
) : Parcelable