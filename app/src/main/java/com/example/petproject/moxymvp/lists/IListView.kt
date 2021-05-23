package com.example.petproject.moxymvp.lists

import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import info.esoft.ko.presentation.common.moxymvp.IView

interface IListView<D> : IView {
    @StateStrategyType(SingleStateStrategy::class)
    fun setData(dataSet: MutableList<D>)

    @StateStrategyType(SingleStateStrategy::class)
    fun clearData()

    @StateStrategyType(SingleStateStrategy::class)
    fun addToData(otherData: MutableList<D>)

    @StateStrategyType(SingleStateStrategy::class)
    fun addItem(item: D)

    @StateStrategyType(SingleStateStrategy::class)
    fun scrollToPosition(position: Int)

    fun updateItemView(item: D, payload: String? = null)

    fun showPopUp(needToShow: Boolean)
}
