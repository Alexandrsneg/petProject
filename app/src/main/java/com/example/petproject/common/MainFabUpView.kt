package com.example.petproject.common

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.example.petproject.R
import com.example.petproject.moxymvp.ABaseView
import kotlinx.android.synthetic.main.main_fab_view.view.*

@SuppressLint("NonConstantResourceId")
@Layout(R.layout.main_fab_view)
class MainFabUpView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ABaseView(context, attrs, defStyleAttr) {

    private var onClickListener: View.OnClickListener? = null

    init {
        mainFabUp.setOnClickListener {
            onClickListener?.onClick(this)
        }

        val a = context.theme.obtainStyledAttributes(
                attrs, R.styleable.MainFABView, defStyleAttr, 0)
        try {
        } finally {
            a.recycle()
        }
    }

    fun hide() {
        mainFabUp.hide()
    }

    fun show() {
        mainFabUp.show()
    }

    override fun setOnClickListener(l: OnClickListener?) {
        onClickListener = l
    }
}