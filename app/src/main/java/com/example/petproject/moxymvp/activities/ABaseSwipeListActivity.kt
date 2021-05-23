package com.example.petproject.moxymvp.activities

import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.petproject.R
import com.example.petproject.moxymvp.lists.IStopPullRefresh

abstract class ABaseSwipeListActivity<D, VH : RecyclerView.ViewHolder> : ABaseListActivity<D, VH>(), SwipeRefreshLayout.OnRefreshListener,
    IStopPullRefresh<D> {

    lateinit var swipeToRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        swipeToRefreshLayout = findViewById(R.id.swipeToRefresh)
        swipeToRefreshLayout.setOnRefreshListener(this)

    }

    override fun stopRefreshing() {
        swipeToRefreshLayout.isRefreshing = false
    }

    override fun showPopUp(needToShow: Boolean) {
    }
}