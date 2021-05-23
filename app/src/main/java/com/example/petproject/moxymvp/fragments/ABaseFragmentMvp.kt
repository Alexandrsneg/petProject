package com.example.petproject.moxymvp.fragments

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.petproject.AppChooserReceiver
import com.example.petproject.R
import com.example.petproject.common.LayoutUtils.getLayoutRes
import com.example.petproject.moxymvp.SimpleNetworkErrorActionView


abstract class ABaseFragmentMvp : MvpAppCompatFragment() {
    internal val tag = this.javaClass.simpleName

    var networkErrorView: SimpleNetworkErrorActionView? = null

    var loadingView: View? = null
    private var isInjected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        if (!isInjected) {
            isInjected = true
            inject()
        }
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        networkErrorView = view.findViewById(R.id.networkErrorView) as SimpleNetworkErrorActionView?
        loadingView = view.findViewById(R.id.loaderView)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutRes(javaClass), container, false)
    }

    open fun showLoadingProgress(show: Boolean) {
        visibility(loadingView, show)
    }

    fun showToast(messageStringResId: Int) {
        Toast.makeText(context, messageStringResId, Toast.LENGTH_SHORT).show()
    }

    fun showToast(message: String?) {
        message?.let { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
    }


    fun showErrorActionView(message: String?, action: () -> Unit) {
        networkErrorView?.let {
            it.visibility = VISIBLE
            it.bind(message, action)
        }
    }

    fun hideErrorActionView() {
        networkErrorView?.let {
            it.visibility = GONE
        }
    }

    fun showKeyboard(v: View) {
        val context = context ?: return
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT)
    }

    fun showShareAppChooser(activity: Activity, uri: Uri?, text: String?, link: String?, shareTypeEntity: String, shareFrom: String, shareObject: Any) {
        AppChooserReceiver.prepareShareContent(shareTypeEntity, shareFrom, shareObject)

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_TEXT, text + "\n" + link)
            type = "image/*"
        }
        val receiver = Intent(activity, AppChooserReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(activity, 0, receiver, PendingIntent.FLAG_UPDATE_CURRENT)
        val chooser = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            Intent.createChooser(shareIntent, null, pendingIntent.intentSender)
        } else {
            Intent.createChooser(shareIntent, "Поделиться с друзьями")
        }
        startActivity(chooser)
    }

    fun visibility(view: View?, value: Boolean) {
        view?.visibility = if (value) VISIBLE else GONE
    }

    open fun showError(message: Int) {
        showError(context?.getString(message) ?: "error")
    }

   open fun showError(message: String?) {
        showToast(message)
    }

    open fun inject() {}
}
