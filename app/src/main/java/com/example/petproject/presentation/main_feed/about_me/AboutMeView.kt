package com.example.petproject.presentation.main_feed.about_me

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.petproject.R
import com.example.petproject.common.Layout
import com.example.petproject.moxymvp.ABaseView
import info.esoft.ko.data.model.rest.guber.AboutMe
import kotlinx.android.synthetic.main.item_main_widget_guber.view.*
import java.util.*

@SuppressLint("NonConstantResourceId")
@Layout(R.layout.item_main_widget_guber)
class AboutMeView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ABaseView(context, attrs, defStyleAttr) {

    private lateinit var data: AboutMe

    init {
        flAction.setOnClickListener {
//            GuberDetailActivity.show(it.context, data)
        }
    }

    fun bind(data: AboutMe) {
        this.data = data
        val localisation: Locale? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resources.configuration.locales.get(0)
        } else {
            null
        }
        val cardContent = data.getLocale(localisation)

        cardContent?.apply {
            tvTitle.text = title
            tvBody.text = desc
            tvAction.text = buttonText ?: context.getString(R.string.title_read_more)
        }

        val url = data.avatar ?: data.avatar
                Glide.with(context)
                        .load(url)
                        .apply(RequestOptions().centerCrop())
                        .placeholder(R.drawable.shape_bg_button_violet_gradient)
                        .into(ivImage)
    }
}