package com.example.petproject.moxymvp.lists

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.ColorRes
import com.example.petproject.R
import com.example.petproject.common.Layout
import com.example.petproject.moxymvp.ABaseView
import kotlinx.android.synthetic.main.simple_empty_view.view.*

@Layout(R.layout.simple_empty_view)
open class SimpleEmptyView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ABaseView(context, attrs, defStyleAttr), IEmptyView {

    override fun setText(text: Int) {
        tvNodDataFoundMessage.setText(text)
    }

    fun setTextColor(@ColorRes color: Int) {
        tvNodDataFoundMessage.setTextColor(context.resources.getColor(color))
    }

}