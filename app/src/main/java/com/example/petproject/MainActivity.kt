package com.example.petproject

import android.os.Bundle
import com.example.petproject.databinding.ActivityMainBinding
import com.example.petproject.moxymvp.activities.ABaseActivity
import com.example.petproject.presentation.main_feed.all.FeedAllFragment
import com.example.petproject.routers.IMainRouter

class MainActivity : ABaseActivity(ActivityMainBinding::class.java, R.id.container), IMainRouter {

    private var feedAllFragment: FeedAllFragment? = null

    private val binding: ActivityMainBinding get() = getViewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        goToFeed()
    }

    override fun goToFeed() {
        if (feedAllFragment == null) feedAllFragment = FeedAllFragment()
        replace(feedAllFragment!!, false, IMainRouter.FEED)
    }

}