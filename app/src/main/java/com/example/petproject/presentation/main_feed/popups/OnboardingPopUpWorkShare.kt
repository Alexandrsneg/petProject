package com.example.petproject.presentation.main_feed.popups

import android.os.Bundle
import android.view.View
import com.example.petproject.R
import com.example.petproject.common.Layout
import kotlinx.android.synthetic.main.work_onboarding_popup.*

@Layout(R.layout.work_onboarding_popup)
class OnboardingPopUpWorkShare : ABaseOnboardingPopUp() {

    var onBtnActionClicked: (() -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        flClose.setOnClickListener {
            dismiss()
        }
        btnAction.setOnClickListener {
            onBtnActionClicked?.invoke()
            dismiss()
        }
    }

}