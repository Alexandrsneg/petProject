package com.example.petproject.data.domain.di.components

import com.example.petproject.data.domain.di.PerActivity
import com.example.petproject.data.domain.di.modules.RepositoriesModule
import com.example.petproject.presentation.main_feed.all.FeedAllFragment
import dagger.Subcomponent


@Subcomponent()
@PerActivity
interface FeedComponent {
    fun inject(feedAllFragment: FeedAllFragment)
}