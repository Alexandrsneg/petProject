package com.example.petproject.presentation.main_feed.popups

import android.os.Bundle
import android.view.View
import com.example.petproject.R
import com.example.petproject.common.Layout
import kotlinx.android.synthetic.main.item_orgs_onboarding_view.*

@Layout(R.layout.item_orgs_onboarding_view)
class OnboardingPopUpAddAddress : ABaseOnboardingPopUp() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAction.setOnClickListener {
            dismiss()
        }
    }
}