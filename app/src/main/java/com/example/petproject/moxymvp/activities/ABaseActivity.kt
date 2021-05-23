package com.example.petproject.moxymvp.activities

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.petproject.AppChooserReceiver
import com.example.petproject.R
import com.example.petproject.common.Layout
import com.example.petproject.common.Utils
import com.example.petproject.presentation.RightMenuActivity
import com.example.petproject.moxymvp.SimpleNetworkErrorActionView


abstract class ABaseActivity : MvpAppCompatActivity() {

    internal val tag = this.javaClass.simpleName
    var networkErrorView: SimpleNetworkErrorActionView? = null

    var loadingView: View? = null
    var focussedView: View? = null
    private var fragmentContainerId = 0
    private var replaceCounter = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
//        overridePendingTransitionEnter()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val layout = javaClass.getAnnotation(Layout::class.java) as Layout
        fragmentContainerId = layout.fragmentContainerId

        setContentView(layout.value)
        initViews()
        initAnimations()
        initListeners()
    }


    protected open fun initViews() {
        networkErrorView = findViewById<SimpleNetworkErrorActionView>(R.id.networkErrorView)
        loadingView = findViewById(R.id.loaderView)
    }

    protected open fun initAnimations() {}

    protected open fun initListeners() {}

    open fun showLoadingProgress(show: Boolean) {
        loadingView?.let {
            if (show)
                it.visibility = VISIBLE
            else
                it.visibility = GONE

        }

    }

    fun showToast(@StringRes messageStringResId: Int) =
            Toast.makeText(this, messageStringResId, Toast.LENGTH_SHORT).show()

    fun showToast(message: String?) =
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()


    open fun showErrorActionView(message: String?, action: () -> Unit) {
        networkErrorView?.let {
            it.visibility = VISIBLE
            it.bind(message, action)
        }
    }

    open fun hideErrorActionView() {
        networkErrorView?.let {
            it.visibility = GONE
        }
    }

    fun showError(@StringRes message: Int) {
    }

    fun showError(message: String?) {
        println(message)
    }

    open fun inject() {}

    override fun onBackPressed() {
        Utils.hideKeyboard(this)
        super.onBackPressed()
    }

    fun showShareAppChooser(uri: Uri?, text: String?, link: String?, shareTypeEntity: String, shareFrom: String, shareObject: Any) {
        AppChooserReceiver.prepareShareContent(shareTypeEntity, shareFrom, shareObject)

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_TEXT, text + "\n" + link)
            type = "image/*"
        }
        val receiver = Intent(this, AppChooserReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, receiver, PendingIntent.FLAG_UPDATE_CURRENT)
        val chooser = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            Intent.createChooser(shareIntent, null, pendingIntent.intentSender)
        } else {
            Intent.createChooser(shareIntent, "Поделиться с друзьями")
        }
        startActivity(chooser)
    }

    protected open fun initToolBar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar?
        toolbar?.let { setSupportActionBar(it) }
    }

    open fun setToolbarTitle(@StringRes resId: Int) {
        supportActionBar?.setTitle(resId)
    }

    open fun setToolbarTitle(title: String?) {
        supportActionBar?.title = title
    }

    open fun showBackArrowAtToolbar(enable: Boolean) {
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(enable)
            it.setDisplayShowHomeEnabled(enable)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_right_menu) {
            RightMenuActivity.runActivity(this)
            return true
        }

        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_global, menu)
        val miRightMenu = menu.findItem(R.id.menu_item_right_menu)
        if (miRightMenu != null) miRightMenu.isVisible = isEnableRightMenu()
        return super.onCreateOptionsMenu(menu)
    }

    protected open fun isEnableRightMenu(): Boolean {
        return true
    }

    open fun hideKeyboard(focussedView: View?) {
        if (focussedView == null) return

        this.focussedView = focussedView

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(focussedView.windowToken, 0)
    }

    open fun showKeyboard(focussedView: View?) {
        if (focussedView == null) return

        this.focussedView = focussedView

        focussedView.isFocusableInTouchMode = true
        focussedView.requestFocus()

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(focussedView, InputMethodManager.SHOW_IMPLICIT)
    }

    open fun overridePendingTransitionEnter() {
//        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    open fun overridePendingTransitionExit() {
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    open fun replace(fragment: Fragment?): Boolean {
        return replace(fragment, null, null)
    }

    open fun replace(fragment: Fragment?, backStack: String?): Boolean {
        return replace(fragment, backStack, null)
    }

    open fun replace(fragment: Fragment?, backStack: String?, tag: String?): Boolean {
        check(fragmentContainerId != 0) { "Fragment container undefined" }
        val manager = supportFragmentManager
        if (manager.isStateSaved) return false
        val transaction = manager
                .beginTransaction()
        if (backStack != null) transaction.addToBackStack(backStack)
        if (tag == null) transaction.replace(fragmentContainerId, fragment!!) else transaction.replace(fragmentContainerId, fragment!!, tag)
        replaceCounter++
        transaction.commit()
        return true
    }


    override fun finish() {
        super.finish()
        overridePendingTransitionExit()
    }
}