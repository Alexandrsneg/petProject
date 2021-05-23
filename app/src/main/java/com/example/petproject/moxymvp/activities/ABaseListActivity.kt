package com.example.petproject.moxymvp.activities

import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petproject.R
import com.example.petproject.moxymvp.lists.AListAdapter
import com.example.petproject.moxymvp.lists.EmptyRecyclerView
import com.example.petproject.moxymvp.lists.IEmptyView
import com.example.petproject.moxymvp.lists.IListView

/**
 * Created by f0x on 21.11.17.
 */

abstract class ABaseListActivity<D, VH : RecyclerView.ViewHolder> : ABaseActivity(), IListView<D> {

    lateinit var recyclerView: EmptyRecyclerView
    var emptyView: IEmptyView? = null
    lateinit var adapter: AListAdapter<D, VH>
    private lateinit var layoutManager: RecyclerView.LayoutManager

    protected var isDoScrollDown = true

    @get:StringRes
    protected abstract val emptyViewText: Int


    override fun initViews() {
        super.initViews()
        recyclerView = findViewById(R.id.rvList)
        findViewById<View>(R.id.emptyView)?.let {
            if (it is IEmptyView)
                emptyView = it
        }
        recyclerView.setEmptyView(emptyView, emptyViewText)
        recyclerView.setHasFixedSize(true)
        initItemDecorator()?.let { recyclerView.addItemDecoration(it) }
        layoutManager = initLayoutManager()
        recyclerView.layoutManager = layoutManager
        adapter = initAdapter()
        recyclerView.adapter = adapter
        initListeners()
    }

    override fun initListeners() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val first = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                if (isDoScrollDown && dy > 0 && layoutManager.childCount + first >= layoutManager.itemCount - 4)
                    onScrollDown()
            }
        })
    }

    fun initItemDecorator(): RecyclerView.ItemDecoration? = null

    override fun clearData() {
        adapter.clear()
    }

    override fun setData(dataSet: MutableList<D>) {
        adapter.data(dataSet)
    }

    override fun addItem(item: D) {
        adapter.addItem(item)
    }


    override fun addToData(otherData: MutableList<D>) {
        adapter.add(otherData)
    }

    override fun updateItemView(item: D, payload: String?) {
        adapter.update(item, payload)
    }

    override fun showPopUp(needToShow: Boolean) {
    }


    protected abstract fun initAdapter(): AListAdapter<D, VH>

    protected open fun initLayoutManager(): RecyclerView.LayoutManager =
            LinearLayoutManager(this)

    override fun scrollToPosition(position: Int) {
        recyclerView.scrollToPosition(position)
    }

    @CallSuper
    protected open fun onScrollDown() {
        isDoScrollDown = false
    }

}
