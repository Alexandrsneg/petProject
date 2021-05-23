package com.example.petproject.moxymvp

import android.content.Context
import android.util.AttributeSet
import com.example.petproject.R
import com.example.petproject.common.Layout
import kotlinx.android.synthetic.main.network_error_action_view.view.*

/**
 * Created by f0x on 12.03.18.
 */
@Layout(R.layout.network_error_action_view)
class SimpleNetworkErrorActionView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ABaseView(context, attrs, defStyleAttr) {
    fun bind(message: String?, action: () -> Unit) {
        tvErrorMessage.text = message
        btnErrorRetry.setOnClickListener { action.invoke() }
    }

}