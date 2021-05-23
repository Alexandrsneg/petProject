package com.example.petproject.presentation.main_feed.popups

import android.content.Context
import android.util.AttributeSet
import com.example.petproject.R
import com.example.petproject.common.Layout
import com.example.petproject.moxymvp.ABaseView
import kotlinx.android.synthetic.main.item_orgs_onboarding_view.view.*

@Layout(R.layout.item_orgs_onboarding_view)
class OnboardingAddressFromOrgView @kotlin.jvm.JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ABaseView(context, attrs, defStyleAttr) {

    init {
        btnAction.setOnClickListener {
//            ProfileActivity.runActivity(it.context, ProfileFragment.KEY_EDIT_FIELD_STREET)
        }
    }

}