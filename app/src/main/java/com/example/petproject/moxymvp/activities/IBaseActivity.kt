package com.example.petproject.moxymvp.activities

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

interface IBaseActivity {

    fun setToolbarTitle(@StringRes titleId: Int)
    fun setToolbarTitle(title: String)
    fun showActionBar(state: Boolean)
    fun showBackArrow(state: Boolean)

    fun toast(@StringRes messageId: Int)
    fun toast(@StringRes messageId: Int, vararg args: Any)
    fun toast(message: String)

    fun add(fragment: Fragment): Boolean
    fun add(fragment: Fragment, backStack: Boolean): Boolean
    fun add(fragment: Fragment, backStack: Boolean, tag: String?): Boolean
    fun add(fragment: Fragment, backStack: Boolean, tag: String?, animation: ABaseActivity.Animation?): Boolean

    fun add(fragment: Fragment, tag: String?): Boolean
    fun add(fragment: Fragment, tag: String?, animation: ABaseActivity.Animation?): Boolean

    fun add(fragment: Fragment, backStack: Boolean, animation: ABaseActivity.Animation?): Boolean

    fun replace(fragment: Fragment): Boolean
    fun replace(fragment: Fragment, backStack: Boolean): Boolean
    fun replace(fragment: Fragment, backStack: Boolean, tag: String?): Boolean
    fun replace(fragment: Fragment, backStack: Boolean, tag: String?, animation: ABaseActivity.Animation?): Boolean

    fun replace(fragment: Fragment, tag: String?): Boolean
    fun replace(fragment: Fragment, tag: String?, animation: ABaseActivity.Animation?): Boolean

    fun replace(fragment: Fragment, backStack: Boolean, animation: ABaseActivity.Animation?): Boolean
}