package com.example.petproject.moxymvp.lists

import android.view.View

interface IListItemView<D> {
    fun bind(item: D, onClickListener: View.OnClickListener? = null)
}
