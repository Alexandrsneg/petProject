package info.esoft.ko.data.model.rest.guber

import android.os.Parcelable
import com.example.petproject.data.model.rest.guber.Contacts
import com.example.petproject.data.model.rest.guber.Localisation
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*
import java.util.Locale.CHINESE
import java.util.Locale.ENGLISH

@Parcelize
data class AboutMe(
    val age: Int? = null,
    val avatar: String? = null,
    val firstName: String? = null,
    val secondName: String? = null,
    val contacts: Contacts? = null,
    val locales: Map<String, Localisation>? = null
) : Parcelable {

    fun getLocale(localisation: Locale?): Localisation? {
        return when (localisation) {
            ENGLISH -> locales?.get("en")
            CHINESE -> locales?.get("cn")
            else -> locales?.get("ru")
        }
    }
}