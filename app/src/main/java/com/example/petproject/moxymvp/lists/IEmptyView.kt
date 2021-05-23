package com.example.petproject.moxymvp.lists

import androidx.annotation.StringRes

interface IEmptyView {
    fun setText(@StringRes text: Int)
    fun setVisibility(i: Int)
}
