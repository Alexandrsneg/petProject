package com.example.petproject.data.domain.di.modules

import com.example.petproject.common.rest.retrofit.IRestClient
import com.example.petproject.data.domain.FeedRestAPI
import com.example.petproject.data.domain.IFeedRestAPI
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RestsModules {

    @Provides
    @Singleton
    fun feedRest(client: IRestClient): IFeedRestAPI = FeedRestAPI(client)

}