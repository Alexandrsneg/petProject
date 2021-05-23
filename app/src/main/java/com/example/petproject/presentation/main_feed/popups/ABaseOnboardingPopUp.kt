package com.example.petproject.presentation.main_feed.popups

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.petproject.R
import com.example.petproject.common.ABaseDialogFragment
import com.example.petproject.common.Layout
import kotlinx.android.synthetic.main.work_onboarding_popup.*

@Layout(R.layout.work_onboarding_popup)
abstract class ABaseOnboardingPopUp : ABaseDialogFragment(), DialogInterface {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        content?.let { setContent(it) }
        title?.let { setTitle(it) }
        imageLink?.let { setImage(it) }
        btnTitle?.let { setButtonTitle(it) }

        btnAction?.setOnClickListener {
            dismiss()
        }
    }

    private var content: String? = null
    private var title: String? = null
    private var imageLink: String? = null
    private var btnTitle: String? = null

    fun setContent(value: String?): ABaseOnboardingPopUp {
        content = value
        tvContent?.let {
            it.text = value
        }
        return this
    }

    fun setTitle(value: String?): ABaseOnboardingPopUp {
        title = value
        tvTitle?.let {
            it.text = value
        }
        return this
    }

    fun setImage(value: String?): ABaseOnboardingPopUp {
        imageLink = value
            context?.let {
                Glide.with(it)
                        .load(value)
                        .into(ivImage)
            }
        return this
    }

    fun setButtonTitle(value: String?): ABaseOnboardingPopUp {
        btnTitle = value
        tvBtnTitle?.let {
            it.text = value ?: ""
        }
        return this
    }


    override fun cancel() {
        onCancel(this)
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.let { window ->
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window.attributes?.let {
                window.attributes = it.apply {
                    width = ViewGroup.LayoutParams.MATCH_PARENT
                    height = ViewGroup.LayoutParams.WRAP_CONTENT
                }
            }
        }
    }

    override fun dismiss() {
        super.dismiss()
    }
}