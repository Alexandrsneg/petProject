package com.example.petproject.presentation.main_feed.all

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petproject.common.MessagesInTheEndedView
import com.example.petproject.moxymvp.lists.AListAdapter
import com.example.petproject.presentation.main_feed.about_me.AboutMeView
import info.esoft.ko.data.model.rest.guber.AboutMe

class FeedAllAdapter : AListAdapter<FeedItem, RecyclerView.ViewHolder>() {

    companion object {
        const val ENDED_TYPE = 1
        const val ABOUT_ME_WIDGET_TYPE = 2
        const val EVENTS_WIDGET_TYPE = 3
    }

//    var onProposalLikeClicked: ((proposal: Proposal) -> Unit)? = null

    //все события
    var onAllEventsClicked: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ENDED_TYPE -> DefaultViewHolder<FeedItemEnded>(MessagesInTheEndedView(parent.context))
            ABOUT_ME_WIDGET_TYPE -> DefaultViewHolder<AboutMe>(AboutMeView(parent.context))
//            EVENTS_WIDGET_TYPE -> DefaultViewHolder<Event>(EventsWidgetView(parent.context))
            else -> DefaultViewHolder<FeedItemEnded>(MessagesInTheEndedView(parent.context)).apply { setIsRecyclable(false) }
        }.apply {
            (itemView as? View)?.let {
                it.layoutParams = RecyclerView.LayoutParams(
                        RecyclerView.LayoutParams.MATCH_PARENT,
                        RecyclerView.LayoutParams.WRAP_CONTENT
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isEmpty())
            return

        val item = getItem(position)
        when (getItemViewType(position)) {

            ABOUT_ME_WIDGET_TYPE -> item.aboutMe?.let { (holder.itemView as AboutMeView).bind(it) }

            EVENTS_WIDGET_TYPE -> {
//                val pv = holder.itemView as EventsWidgetView
//                pv.bind(item.events, item.title, item.subTitle)
//                pv.onAllEventsClicked = onAllEventsClicked
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        }

        val item = getItem(position)
        payloads.forEach {
            when (it.toString()) {
//                ProposalsListAdapter.PROPOSAL_LIKE_STATE_CHANGED -> {
//                    val proposalView = holder.itemView
//                    val proposals = item.proposals
//                    if (proposals != null && proposalView is ProposalsWidgetView) {
//                       (holder.itemView as ProposalsWidgetView).showLikeState()
//                    }
//                }
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return data()[position].getItemViewType()
    }
}