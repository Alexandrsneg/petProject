package com.example.petproject.moxymvp

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import com.example.petproject.common.Layout


abstract class ABaseView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        val layout = javaClass.getAnnotation(Layout::class.java) as Layout
        inflate(context, layout.value, this)
    }

    fun hideKeyBoard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun visibility(view: View?, value: Boolean) {
        view?.visibility = if (value) View.VISIBLE else View.GONE
    }

    fun visibility(view: ViewGroup?, value: Boolean) {
        view?.visibility = if (value) View.VISIBLE else View.GONE
    }
}
