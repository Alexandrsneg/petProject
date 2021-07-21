package com.example.petproject.presentation.main_feed.all

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.petproject.App
import com.example.petproject.AppChooserReceiver
import com.example.petproject.MainActivity
import com.example.petproject.R
import com.example.petproject.moxymvp.activities.ABaseActivity
import com.example.petproject.moxymvp.lists.AListAdapter
import com.example.petproject.moxymvp.fragments.ABasePullListFragmentMvp
import com.example.petproject.presentation.main_feed.popups.OnboardingModel
import com.example.petproject.presentation.main_feed.popups.OnboardingPopUpAddAddress
import kotlinx.android.synthetic.main.fragment_feed_all.*
import javax.inject.Inject


class FeedAllFragment : ABasePullListFragmentMvp<FeedItem, RecyclerView.ViewHolder>(R.layout.fragment_feed_all),
    IFeedAllList {

    override val emptyViewText = 0

    @Inject
    @InjectPresenter
    lateinit var presenter: FeedAllPresenter

    @ProvidePresenter
    fun provide() = presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.context = context
        activity?.let {
            if (it is ABaseActivity)
                it.setSupportActionBar(toolbar)
        }
        rvList?.descendantFocusability = ViewGroup.FOCUS_BLOCK_DESCENDANTS

        rvList?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val firstElementPosition = (recyclerView.layoutManager  as LinearLayoutManager).findFirstVisibleItemPosition()
                val lastElementPosition = (recyclerView.layoutManager  as LinearLayoutManager).findLastVisibleItemPosition()
            }
        })
//        setHasOptionsMenu(true)

        fabUp?.setOnClickListener {
            smoothScrollToPosition(0)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_global, menu)
    }

    override fun initAdapter(): AListAdapter<FeedItem, RecyclerView.ViewHolder> {
        return FeedAllAdapter().apply {

//            onProposalsShareClickListener = object : ProposalsListAdapter.OnShareClickListener {
//                override fun onShareClicked(proposal: Proposal) {
//                    presenter.onShareProposalClicked(proposal)
//                }
//            }
            onAllEventsClicked = {
//                context?.let { EventsActivity.show(it) }
            }
        }
    }

    override fun initLayoutManager(): RecyclerView.LayoutManager = LinearLayoutManager(context)

    override fun inject() {
        App.appComponent.plusFeedComponent().inject(this)
    }

    override fun onRefresh() {
        presenter.onRefresh()
    }

    override fun addToData(otherData: MutableList<FeedItem>) {
        adapter.add(otherData)
        if (otherData.last() is FeedItemEnded)
            return
        releaseScroll()
    }

    override fun setData(dataSet: MutableList<FeedItem>) {
        super.setData(dataSet)
        releaseScroll()
    }

    override fun showError(@StringRes message: Int) {
        (activity as MainActivity).showError(message)
    }

    override fun showError(message: String?) {
        (activity as MainActivity).showError(message)
    }

    override fun showShareAppChooser(uri: Uri?, text: String?, link: String?, shareType: String, shareObject: Any) {
        val activity = activity as MainActivity? ?: return
        showShareAppChooser(activity, uri, text, link, shareType, AppChooserReceiver.SHARE_FROM_MAIN_FEED, shareObject)
    }

    override fun showMainScreenToolbar(title: String) {
//        tvCityName.text = cityName
    }

    override fun showOnboardingPopUp(onboardingModel: OnboardingModel) {
        val onboardingPopUp = OnboardingPopUpAddAddress().apply {
            isCancelable = false
            setTitle(onboardingModel.title)
            setContent(onboardingModel.content)
            setImage(onboardingModel.image?.original)
            setButtonTitle(onboardingModel.buttonText)
        }
        fragmentManager?.let{
            onboardingPopUp.show(it, "ONBOARDING_POPUP_SHOWED")
        }
    }
}