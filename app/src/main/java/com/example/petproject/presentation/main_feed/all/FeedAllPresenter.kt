package com.example.petproject.presentation.main_feed.all

import com.arellomobile.mvp.InjectViewState
import com.example.petproject.data.domain.IFeedRepository
import com.example.petproject.utils.SubRX
import info.esoft.ko.presentation.common.moxymvp.ABasePresenter
import javax.inject.Inject

@InjectViewState
class FeedAllPresenter @Inject constructor(
    private val interactor: IFeedRepository
) : ABasePresenter<IFeedAllList>() {

    companion object {
        const val IS_LIKE_CLICKED = "IS_LIKE_CLICKED"
    }

    private var items = mutableListOf<FeedItem>()

    private var needRefreshContent = true

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showLoadingProgress(true)
    }

    override fun attachView(view: IFeedAllList?) {
        super.attachView(view)
        viewState.showCityToolbar( "Главная лента")

        if (needRefreshContent) {
            onRefresh()
        }
    }

    fun onRefresh() {
        needRefreshContent = false
        items = mutableListOf()
        loadWidgetsFeed()
    }

    private fun loadWidgetsFeed() {
        interactor.loadWidgetFeed(SubRX { widgets, th ->
            widgets?.forEach { widget ->
                when (widget.id) {
                    WidgetFeed.ABOUT_ME_WIDGET -> {
                        widget.guber?.let {
                            items.add(FeedItem(
                                type = FeedItem.FEED_ITEM_GUBER_WIDGET,
                                aboutMe = it
                            ))
                        }
                    }

                    WidgetFeed.EVENTS_WIDGET -> {
//                        widget.events?.let {
//                            items.add(FeedItem(type = FeedItem.FEED_ITEM_EVENTS_WIDGET,
//                                events = it,
//                                title = widget.title,
//                                subTitle = widget.subTitle,
//                                slidersFirstCard = widget.slidersFirstCard))
//                        }
                    }
                }
            }
            viewState.stopRefreshing()
            viewState.setData(items)
            viewState.showLoadingProgress(false)

            th?.let {
                viewState.stopRefreshing()
            }
        })
    }

//    fun onLikeStatusNewsChanged(news: News, likesStatistic: EntityLikesStatistic?) {
//        items.firstOrNull { it.newsList != null }?.let { feedItem ->
//            feedItem.newsList?.firstOrNull { it.id == news.id }.let {
//                it?.likesStatistic = likesStatistic
//                viewState.updateItemView(feedItem, NewsAdapter.NEWS_LIKE_STATE_CHANGED)
//            }
//        }
//    }

    var visibleItemsNow: MutableList<Int> = mutableListOf()
}