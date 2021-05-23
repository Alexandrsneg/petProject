package com.example.petproject.common

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.example.petproject.moxymvp.fragments.MvpAppCompatDialogFragment

abstract class ABaseDialogFragment : MvpAppCompatDialogFragment() {

    interface IOnHideListener {
        fun onHide(fragment: ABaseDialogFragment)
    }

    private var onHideListener: IOnHideListener? = null

    private var baseView: View? = null
    private var unbinder: Unbinder? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val layoutId = LayoutUtils.getLayoutRes(this.javaClass)
        if (layoutId != 0) {
            baseView = inflater.inflate(layoutId, container, false)?.apply {
                unbinder = ButterKnife.bind(this@ABaseDialogFragment, this)
            }
            return baseView
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    open fun inject() {}


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onHideListener?.onHide(this)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        unbinder?.let {
            it.unbind()
            unbinder = null
        }
    }

    override fun onDetach() {
        super.onDetach()
        unbinder?.let {
            it.unbind()
            unbinder = null
        }
    }


    override fun show(manager: FragmentManager, tag: String?) {
        if (!manager.isStateSaved)
            super.show(manager, tag)


    }

    fun visibility(view: View?, value: Boolean) {
        view?.visibility = if (value) View.VISIBLE else View.GONE
    }

    fun setOnHideListener(onHideListener: IOnHideListener?): ABaseDialogFragment {
        this.onHideListener = onHideListener
        return this
    }
}