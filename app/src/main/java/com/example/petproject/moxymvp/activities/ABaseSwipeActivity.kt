package com.example.petproject.moxymvp.activities

import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.petproject.R
import com.example.petproject.moxymvp.activities.ABaseActivity


/**
 * Created by f0x on 29.10.17.
 */

abstract class ABaseSwipeActivity : ABaseActivity(), SwipeRefreshLayout.OnRefreshListener {
    lateinit var swipeToRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        swipeToRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipeToRefresh)
        swipeToRefreshLayout.setOnRefreshListener(this)

    }
}

