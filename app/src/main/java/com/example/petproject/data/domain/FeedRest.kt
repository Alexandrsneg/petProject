package com.example.petproject.data.domain

import com.example.petproject.common.rest.retrofit.IRestClient
import com.example.petproject.presentation.main_feed.all.WidgetFeed
import retrofit2.http.*
import rx.Observable
import javax.inject.Inject

interface FeedRestApiService {
    @GET("http://someAPI")
    fun loadWidgetFeed(): Observable<MutableList<WidgetFeed>?>
}

interface IFeedRestAPI {
    fun loadWidgetFeed(): Observable<MutableList<WidgetFeed>?>
}

class FeedRestAPI @Inject constructor(restClient: IRestClient) : IFeedRestAPI {
    private val service = restClient.createService(FeedRestApiService::class.java)

    override fun loadWidgetFeed(): Observable<MutableList<WidgetFeed>?> = service.loadWidgetFeed()

}


