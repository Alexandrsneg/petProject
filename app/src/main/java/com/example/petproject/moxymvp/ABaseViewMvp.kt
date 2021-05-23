package info.esoft.ko.presentation.common.moxymvp

import android.content.Context
import android.util.AttributeSet
import com.arellomobile.mvp.MvpDelegate
import com.arellomobile.mvp.MvpView
import com.example.petproject.moxymvp.ABaseView

/**
 * Created by f0x on 07.10.17.
 */

abstract class ABaseViewMvp @JvmOverloads constructor(context: Context,
                                                      attrs: AttributeSet? = null,
                                                      defStyleAttr: Int = 0) :
        ABaseView(context, attrs, defStyleAttr), MvpView {


    var mvpDelegate: MvpDelegate<MvpView> = MvpDelegate(this as MvpView)

    var parentDelegate: MvpDelegate<*>? = null
        set(value) {
            field = value
            mvpDelegate.setParentDelegate(parentDelegate, id.toString())
            mvpDelegate.onCreate()
            mvpDelegate.onAttach()
        }

    init {
        this.inject()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mvpDelegate.onSaveInstanceState()
        mvpDelegate.onDetach()

    }

    protected abstract fun inject()
}
