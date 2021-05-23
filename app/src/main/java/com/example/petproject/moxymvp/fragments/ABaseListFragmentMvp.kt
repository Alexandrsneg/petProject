package com.example.petproject.moxymvp.fragments

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petproject.R
import com.example.petproject.common.MainFabUpView
import com.example.petproject.moxymvp.lists.*

/**
 * Created by f0x on 13.07.17.
 */

abstract class ABaseListFragmentMvp<D, VH : RecyclerView.ViewHolder> : ABaseFragmentMvp(),
    IListView<D> {

    lateinit var recyclerView: EmptyRecyclerView
    var emptyView: IEmptyView? = null
    protected lateinit var adapter: AListAdapter<D, VH>
    protected lateinit var layoutManager: RecyclerView.LayoutManager
    protected var isDoScrollDown = true
    var fabUp: MainFabUpView? = null

//    open var isShowDivider: Boolean = true


    @get:StringRes
    protected open val emptyViewText: Int = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rvList) as EmptyRecyclerView
        emptyView = view.findViewById<SimpleEmptyView>(R.id.emptyView)
//        fabUp = view.findViewById(R.id.fabUp)
        fabUp?.hide()

        val emptyViewText = emptyViewText
        if (emptyViewText != 0)
            recyclerView.setEmptyView(emptyView, emptyViewText)
        recyclerView.setHasFixedSize(true)
//        if (isShowDivider)
//            recyclerView.addItemDecoration(createItemDecorator())
        recyclerView.itemAnimator = DefaultItemAnimator()
        layoutManager = initLayoutManager()
        recyclerView.layoutManager = layoutManager
        adapter = initAdapter()
        recyclerView.adapter = adapter
        initListeners()
    }

    open fun initListeners() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val first = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                if (isDoScrollDown && dy > 0 && layoutManager.childCount + first >= layoutManager.itemCount - 4)
                    onScrollDown()
                if (first > 1) fabUp?.show() else fabUp?.hide()
            }
        })
    }


    override fun clearData() {
        adapter.clear()
    }

    override fun setData(dataSet: MutableList<D>) {
        adapter.data(dataSet)
    }


    override fun updateItemView(item: D, payload: String?) {
        adapter.update(item, payload)
    }

    @CallSuper
    protected open fun onScrollDown() {
        isDoScrollDown = false
    }

    @CallSuper
    protected open fun releaseScroll() {
        isDoScrollDown = true
    }

    override fun addItem(item: D) {
        adapter.addItem(item)
    }

    override fun scrollToPosition(position: Int) {
        recyclerView.scrollToPosition(position)
    }

    override fun showPopUp(needToShow: Boolean) {
    }


    protected abstract fun initAdapter(): AListAdapter<D, VH>

    protected open fun initLayoutManager() = LinearLayoutManager(context)
}
