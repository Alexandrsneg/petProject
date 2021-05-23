package com.example.petproject.routers

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface IBaseRouter {
    @StateStrategyType(SkipStrategy::class)
    fun onBackPressed()
}