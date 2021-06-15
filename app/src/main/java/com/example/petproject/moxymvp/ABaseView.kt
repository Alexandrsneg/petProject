package com.example.petproject.moxymvp

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import com.example.petproject.common.Layout


abstract class ABaseView : LinearLayout {

    private @LayoutRes val layoutId: Int

    @JvmOverloads
    constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
    ) : this(0, context, attrs, defStyleAttr)

    @JvmOverloads
    constructor(@LayoutRes layoutId: Int,
                context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr) {
        this.layoutId = layoutId
        init()
    }

    private fun init() {
        var layoutId = layoutId
        if (layoutId == 0)
            layoutId = (javaClass.getAnnotation(Layout::class.java) as Layout).value
        inflate(context, layoutId, this)
    }

    fun visibility(view: View?, value: Boolean) {
        view?.visibility = if (value) View.VISIBLE else View.GONE
    }

    fun visibility(view: ViewGroup?, value: Boolean) {
        view?.visibility = if (value) View.VISIBLE else View.GONE
    }

    open fun hideKeyboard(view: View? = null) {
        val windowToken = view?.windowToken ?: windowToken
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }

    open fun showKeyboard(view: View?) {
        view ?: return

        view.isFocusableInTouchMode = true
        view.requestFocus()

        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}
