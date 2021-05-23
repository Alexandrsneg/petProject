package com.example.petproject.data.model.rest

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Attachment(

        @SerializedName("attachment_id")
        val id: Int,
        val original: String,
        val preview: String?,
        val filename: String?,
        val type: AttachmentType?,
        val createdAt: Date?

) : Parcelable

enum class AttachmentType {
        image, document
}