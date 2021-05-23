package com.example.petproject.data.domain.di.modules

import com.example.petproject.data.domain.FeedRepository
import com.example.petproject.data.domain.IFeedRepository
import com.example.petproject.data.domain.IFeedRestAPI
import com.example.petproject.data.domain.di.PerActivity
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module
class RepositoriesModule {
    @Singleton
    @Provides
    fun provideFeedRepository(api: IFeedRestAPI): IFeedRepository {
        return FeedRepository(api)
    }
}