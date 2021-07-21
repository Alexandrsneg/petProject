package com.example.petproject.presentation.main_feed.all

import android.net.Uri
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.petproject.moxymvp.lists.IStopPullRefresh
import com.example.petproject.presentation.main_feed.popups.OnboardingModel

interface IFeedAllList : IStopPullRefresh<FeedItem> {

    @StateStrategyType(SkipStrategy::class)
    fun showShareAppChooser(uri: Uri?, text: String?, link: String?, shareType: String, shareObject: Any)

    fun showMainScreenToolbar(title: String)

    fun showOnboardingPopUp(onboardingModel: OnboardingModel)
}