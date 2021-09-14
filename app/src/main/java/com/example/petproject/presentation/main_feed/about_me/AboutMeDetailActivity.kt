package com.example.petproject.presentation.main_feed.about_me

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.petproject.R
import com.example.petproject.common.Utils
import com.example.petproject.common.views.AttachmentDocumentView
import com.example.petproject.common.views.InfoItemView
import com.example.petproject.data.model.rest.Attachment
import com.example.petproject.data.model.rest.Common.Link
import com.example.petproject.databinding.AboutMeDetailBinding
import com.example.petproject.moxymvp.activities.ABaseActivity
import java.util.*

class AboutMeDetailActivity : ABaseActivity(AboutMeDetailBinding::class.java) {

    companion object {
        private const val GUBER_KEY = "GUBER_KEY"
        private const val IS_NEED_SCROLL = "IS_NEED_SCROLL"

        fun show(context: Context?) {
            context?.startActivity(Intent(context, AboutMeDetailActivity::class.java))
        }
    }

    private val binding: AboutMeDetailBinding get() = getViewBinding()

    override fun provideToolbar(): Toolbar? {
        return findViewById<View>(R.id.toolbar) as Toolbar?
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        this.onCreateGlobalBurgerMenu(menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (this.runGlobalBurgerMenu(item))
//            return true
//        return super.onOptionsItemSelected(item)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val guber = getMeDetail() // todo добавить в FB модель деталки, попробовать через MVVM и корутины

        //для многострочного отображения заголовка на устройствах с низким разрешением
        val toolbarTitle = findViewById<TextView>(R.id.toolbarMultilineTitle)
        toolbarTitle?.apply {
            text = "Детвльно обо мне"
            setToolbarTitle("")
        }
        showBackArrow(true)

        val localisation: Locale? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resources.configuration.locales.get(0)
        } else {
            null
        }

        getScroll()?.let {
            if (it)
                binding.svScrollView.postDelayed(Runnable {
                    binding.svScrollView.smoothScrollTo(0,  binding.llVideoContainer.bottom)
                }, 500)
        }


        guber?.let {
            val url = it.image
            Glide.with(this)
                .load(url)
                .apply(RequestOptions().centerCrop())
                .placeholder(R.drawable.bg_image_placeholder)
                .into( binding.ivImage)

            it.getLocale(localisation)?.apply {
                binding.llDocuments.removeAllViews()
                attachments?.forEach { attachment ->
                    addDocument(this@GuberDetailActivity, attachment)
                }
                binding.tvTitle.text = title
                binding.tvContent.text = content
                binding.tvSubDesc.text = subDesc
            }

            binding.tvLinksBlockTitle.text = it.linksBlockTitle ?: "Видеоотчеты"

            it.contacts?.apply {
                binding.iivMail.message = mail ?: ""
                binding.iivMail.setOnClickListener {
                    Utils.openMailApp(this@GuberDetailActivity, mail)
                }
                binding.iivPhone.message = phoneDescription ?: ""
                binding.iivPhone.setOnClickListener {
                    Utils.openPhoneCall(this@GuberDetailActivity, phoneDescription)
                }

                binding.llSites.removeAllViews()
                links?.forEach { site ->
                    addInfoView(this@GuberDetailActivity, site)
                }
            }
        } ?: finish()
    }

    //добавление ПДФок
    private fun addDocument(context: Context, attachment: Attachment) {
        binding.llDocuments.visibility = View.VISIBLE
        binding.llDocuments.addView(AttachmentDocumentView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            bind(attachment)
            setOnClickListener {
            }
        })
    }

    //добавление ссылок на сайты
    private fun addInfoView(context: Context, link: Link) {
        binding.llSites.visibility = View.VISIBLE
        if (binding.llSites.childCount > 0)
            binding.llSites.addView(FrameLayout(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    Utils.dp2px(1f, context).toInt()
                )
                setBackgroundColor(Color.parseColor("#80B1B3C9"))
            })

        binding.llSites.addView(InfoItemView(this).apply {
            iconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_globe_blue)
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            bind(link)
            setOnClickListener {
            }
        })
    }
}