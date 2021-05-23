package com.example.petproject

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.petproject.common.Layout
import com.example.petproject.moxymvp.activities.ABaseActivity
import com.example.petproject.presentation.main_feed.all.FeedAllFragment
import com.example.petproject.routers.IMainRouter

@SuppressLint("NonConstantResourceId")
@Layout(value = R.layout.activity_main, fragmentContainerId = R.id.container)
class MainActivity : ABaseActivity(), IMainRouter {
    private var feedAllFragment: FeedAllFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        goToFeed()
    }

    override fun goToFeed() {
        if (feedAllFragment == null) feedAllFragment = FeedAllFragment()
        replace(feedAllFragment, null, IMainRouter.FEED)
    }

}