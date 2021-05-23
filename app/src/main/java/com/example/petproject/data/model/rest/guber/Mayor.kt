package info.esoft.ko.data.model.rest.guber

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Mayor(
        @SerializedName("widget_image") val widgetImage: String? = null,
        val image: String? = null,
        val title: String? = null,
        @SerializedName("content_title") val contentTitle: String? = null,
        val description: String? = null,
        val content: String? = null,
        @SerializedName("button_text") val buttonText: String? = null,
        @SerializedName("button_link") val buttonLink: String? = null,
        @SerializedName("is_detail") val isDetail: Boolean? = null
) : Parcelable