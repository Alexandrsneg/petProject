package com.example.petproject.moxymvp.lists

interface IStopPullRefresh<D> : IListView<D> {
    fun stopRefreshing()
}