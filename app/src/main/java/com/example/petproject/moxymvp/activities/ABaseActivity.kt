package com.example.petproject.moxymvp.activities

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewbinding.ViewBinding
import com.example.petproject.AppChooserReceiver
import com.example.petproject.R
import com.example.petproject.common.Layout
import com.example.petproject.common.Utils
import com.example.petproject.presentation.RightMenuActivity
import com.example.petproject.moxymvp.SimpleNetworkErrorActionView


abstract class ABaseActivity : MvpAppCompatActivity, IBaseActivity {

    data class Animation(
        @AnimatorRes @AnimRes val enter: Int,
        @AnimatorRes @AnimRes val exit: Int,
        @AnimatorRes @AnimRes val popEnter: Int = 0,
        @AnimatorRes @AnimRes val popExit: Int = 0
    )

    companion object {

        fun applyAnimation(transaction: FragmentTransaction, anim: Animation) {

            if (anim.enter != 0 && anim.exit != 0 && anim.popEnter != 0 && anim.popExit != 0)
                transaction.setCustomAnimations(anim.enter, anim.exit, anim.popEnter, anim.popExit)
            else if (anim.enter != 0 && anim.exit != 0)
                transaction.setCustomAnimations(anim.enter, anim.exit)
        }
    }

    @LayoutRes
    val layoutId: Int
    @IdRes
    val containerId: Int

    private val viewBinding: Class<*>?
    private var bindingInstance: ViewBinding? = null
    private var toast: Toast? = null
//    protected val layout: Layout? = javaClass.layoutAnnotation()

    open fun inject() { }
    open fun provideToolbar(): Toolbar? = null

    @JvmOverloads
    constructor(@LayoutRes layoutId: Int = 0, @IdRes containerId: Int = 0) : super() {
        this.layoutId = layoutId
        this.containerId = containerId
        viewBinding = null
    }

    @JvmOverloads
    constructor(viewBinding: Class<*>, @IdRes containerId: Int = 0) : super() {
        this.layoutId = 0
        this.containerId = containerId
        this.viewBinding = viewBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        inject()
        super.onCreate(savedInstanceState)

        if (layoutId != 0)
            setContentView(layoutId)

        viewBinding?.also {
            val binding = it.getDeclaredMethod("inflate", LayoutInflater::class.java)
                .invoke(null, layoutInflater)
            bindingInstance = binding as ViewBinding
            setContentView(binding.root)
        }

        provideToolbar()?.let { setSupportActionBar(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun setToolbarTitle(@StringRes titleId: Int) = setToolbarTitle(getString(titleId))

    override fun setToolbarTitle(title: String) {
        supportActionBar?.let {
            if (title.isNotEmpty())
                showActionBar(true)
            it.title = title
        }
    }

    override fun showActionBar(state: Boolean) {
        supportActionBar?.let {
            if (state) it.show()
            else it.hide()
        }
    }

    override fun showBackArrow(state: Boolean) {
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(state)
            if (state) showActionBar(true)
        }
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.lastOrNull()?.let {
            if (it is IOnBackPressListener && it.onBackPressed())
                return
        }
        super.onBackPressed()
    }

    @JvmOverloads
    fun visible(view: View?, state: Boolean = true) {
        view?.visibility = if (state) View.VISIBLE else View.GONE
    }

    fun <T : ViewBinding> getViewBinding() = bindingInstance as? T
        ?: throw IllegalStateException("Need call constructor with define viewBinding type")

    override fun toast(messageId: Int) = toast(getString(messageId))

    override fun toast(messageId: Int, vararg args: Any) = toast(getString(messageId, *args))

    @Synchronized
    override fun toast(message: String) {

        toast?.let {
            toast = null
            it.cancel()
        }

        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT).apply { show() }
    }


    override fun add(fragment: Fragment) =
        internalAdd(fragment, false, null, null)
    override fun add(fragment: Fragment, backStack: Boolean) =
        internalAdd(fragment, backStack, null, null)
    override fun add(fragment: Fragment, backStack: Boolean, tag: String?) =
        internalAdd(fragment, backStack, tag, null)
    override fun add(fragment: Fragment, backStack: Boolean, tag: String?, animation: Animation?) =
        internalAdd(fragment, backStack, tag, animation)
    override fun add(fragment: Fragment, tag: String?) =
        internalAdd(fragment, false, tag, null)
    override fun add(fragment: Fragment, tag: String?, animation: Animation?) =
        internalAdd(fragment, false, tag, animation)
    override fun add(fragment: Fragment, backStack: Boolean, animation: Animation?) =
        internalAdd(fragment, backStack, null, animation)


    override fun replace(fragment: Fragment) =
        internalReplace(fragment, false, null, null)
    override fun replace(fragment: Fragment, backStack: Boolean) =
        internalReplace(fragment, backStack, null, null)
    override fun replace(fragment: Fragment, backStack: Boolean, tag: String?) =
        internalReplace(fragment, backStack, tag, null)
    override fun replace(fragment: Fragment, backStack: Boolean, tag: String?, animation: Animation?) =
        internalReplace(fragment, backStack, tag, animation)
    override fun replace(fragment: Fragment, tag: String?) =
        internalReplace(fragment, false, tag, null)
    override fun replace(fragment: Fragment, tag: String?, animation: Animation?) =
        internalReplace(fragment, false, tag, animation)
    override fun replace(fragment: Fragment, backStack: Boolean, animation: Animation?) =
        internalReplace(fragment, backStack, null, animation)


    private fun internalAdd(
        fragment: Fragment,
        backStack: Boolean,
        tag: String?,
        animation: Animation?
    ) = try {

        if (!backStack)
            for (i in 0 until supportFragmentManager.backStackEntryCount)
                supportFragmentManager.popBackStack()

        supportFragmentManager.beginTransaction()
            .add(containerId, fragment, tag)
            .apply {
                animation?.also { applyAnimation(this, it) }

                if (backStack)
                    addToBackStack("${fragment.javaClass}")
            }
            .commit()
        true

    } catch (th: Throwable) {
        th.printStackTrace()
        false
    }

    private fun internalReplace(
        fragment: Fragment,
        backStack: Boolean,
        tag: String?,
        animation: Animation?
    ) = try {

        if (!backStack)
            for (i in 0 until supportFragmentManager.backStackEntryCount)
                supportFragmentManager.popBackStack()

        supportFragmentManager.beginTransaction()
            .replace(containerId, fragment, tag)
            .apply {
                animation?.also { applyAnimation(this, it) }

                if (backStack)
                    addToBackStack("${fragment.javaClass}")
            }
            .commit()
        true

    } catch (th: Throwable) {
        th.printStackTrace()
        false
    }

    fun onCreateGlobalBurgerMenu(menu: Menu){
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_global, menu)
        menu.findItem(R.id.menu_item_right_menu)?.apply {
            actionView.setOnClickListener { onOptionsItemSelected(this) }
        }
    }

    fun runGlobalBurgerMenu(item: MenuItem) : Boolean{
        if (item.itemId == R.id.menu_item_right_menu) {
            RightMenuActivity.runActivity(this)
            return true
        }
        return false
    }

    protected open fun initViews() {
    }

    protected open fun initAnimations() {}

    protected open fun initListeners() {}
}