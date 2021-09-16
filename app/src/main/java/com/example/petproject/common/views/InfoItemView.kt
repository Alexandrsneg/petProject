package com.example.petproject.common.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.content.res.AppCompatResources
import com.example.petproject.R
import com.example.petproject.common.Utils
import com.example.petproject.data.model.rest.guber.Site
import com.example.petproject.moxymvp.ABaseView
import kotlinx.android.synthetic.main.info_item_view.view.*


class InfoItemView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ABaseView(R.layout.info_item_view, context, attrs, defStyleAttr) {

    private var onClickListener: OnClickListener? = null

    fun bind(link: Site){
        tvMessage.apply {
            text = link.title
            setOnClickListener {
                Utils.openUrlInBrowser(it.context, link.url)
                onClickListener?.onClick(this)
            }
        }
    }

    var iconDrawable: Drawable? = null
        set(value) {
            field = value
            ivIcon.setImageDrawable(value)
        }

    var message: String = ""
        set(value) {
            field = value
            tvMessage.text = value
        }

    var expanded: Boolean = false
        set(value) {
            field = value
            visibility(flToggleContainer, value)
        }

    init {
        tvMessage.setOnClickListener { onClickListener?.onClick(this) }
        val a = context.theme.obtainStyledAttributes(
                attrs, R.styleable.InfoItemView, defStyleAttr, 0)
        try {
            message = a.getString(R.styleable.InfoItemView_iiv_message) ?: ""
            expanded = a.getBoolean(R.styleable.InfoItemView_iiv_expanded, false)

            val iconDrawableResId = a.getResourceId(R.styleable.InfoItemView_iiv_icon, -1);
            if (iconDrawableResId != -1)
                AppCompatResources.getDrawable(getContext(), iconDrawableResId)?.let { iconDrawable = it }

            a.getDimensionPixelSize(R.styleable.InfoItemView_iiv_iconMarginEnd, 24).let {
                    val params: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                    params.marginEnd = it
                    ivIcon.layoutParams = params
            }

        } finally {
            a.recycle()
        }
    }

    open fun showIcon(show: Boolean){
        if (show) ivIcon.visibility = View.VISIBLE else ivIcon.visibility = View.INVISIBLE
    }

    override fun setOnClickListener(l: OnClickListener?) {
        onClickListener = l
    }
}
